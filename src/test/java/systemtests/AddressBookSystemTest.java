package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;
import static systemtests.ClockRule.INJECTED_CLOCK;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.MainApp;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.ui.CommandBox;

/**
 * A system test class for AddressBook, which provides access to handles of GUI components, and
 * verifies that the starting state of the application is correct.
 */
public abstract class AddressBookSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupUpBeforeClass() {
        SystemTestSetupHelper.initializeStage();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication();
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() throws Exception {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public PersonListPanelHandle getPersonListPanel() {
        return mainWindowHandle.getPersonListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Runs {@code command} in the application's {@code CommandBox}.
     */
    public void runCommand(String command) {
        mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    protected void rememberStates() throws Exception {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getPersonListPanel().rememberSelectedPersonCard();
    }

    /**
     * Asserts that the browser's url is changed.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertBrowserUrlChanged() throws Exception {
        assertTrue(getBrowserPanel().isUrlChanged());
    }

    /**
     * Asserts that the browser's url remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertBrowserUrlUnchanged() throws Exception {
        assertFalse(getBrowserPanel().isUrlChanged());
    }

    /**
     * Asserts that the selected card in the person list panel is changed.
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardChanged() throws Exception {
        assertTrue(getPersonListPanel().isSelectedPersonCardChanged());
    }

    /**
     * Asserts that the selected card in the person list panel remains unchanged.
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardUnchanged() throws Exception {
        assertFalse(getPersonListPanel().isSelectedPersonCardChanged());
    }

    /**
     * Asserts that the command box shows {@code expected}.
     */
    protected void assertCommandBoxShows(String expected) {
        assertEquals(expected, getCommandBox().getInput());
    }

    /**
     * Asserts that the command box's style is the default style.
     */
    protected void assertCommandBoxStyleDefault() {
        // TODO: We can merge this with assertCommandBoxShows(String) if we disallow users to press enter with no input
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's style is the error style.
     */
    protected void assertCommandBoxStyleError() {
        // TODO: We can merge this with assertCommandBoxShows(String) if we disallow users to press enter with no input
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the result box shows {@code expected}.
     */
    protected void assertResultBoxShows(String expected) {
        assertEquals(expected, getResultDisplay().getText());
    }

    /**
     * Asserts that the address book saved in the storage equals {@code expected}.
     */
    protected void assertSavedAddressBookEquals(ReadOnlyAddressBook expected) {
        assertEquals(expected, getTestApp().readStorageAddressBook());
    }

    /**
     * Asserts that the current model equals {@code expected}.
     */
    protected void assertModelEquals(Model expected) {
        assertEquals(expected, getTestApp().getModel());
    }

    /**
     * Asserts that the person list panel displays the model's filtered list correctly; that is, the UI
     * is correctly bounded to the Model.
     */
    protected void assertPersonListPanelBounded() throws Exception {
        assertListMatching(getPersonListPanel(), getTestApp().getModel().getFilteredPersonList());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#INJECTED_CLOCK}, while the save location remains the same.
     */
    protected void assertOnlySyncStatusChanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(INJECTED_CLOCK.millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        try {
            assertCommandBoxShows("");
            assertResultBoxShows("");
            assertPersonListPanelBounded();
            assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getBrowserPanel().getLoadedUrl());
            assertEquals("./" + getTestApp().getStorageSaveLocation(), getStatusBarFooter().getSaveLocation());
            assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
        } catch (Exception e) {
            throw new AssertionError("Starting state is wrong.", e);
        }
    }

    public TestApp getTestApp() {
        return testApp;
    }
}

package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;
import static systemtests.ClockRule.INJECTED_CLOCK;
import static systemtests.SystemTestSetup.initializeStage;
import static systemtests.SystemTestSetup.setupApplication;
import static systemtests.SystemTestSetup.setupMainWindowHandle;
import static systemtests.SystemTestSetup.tearDownStage;

import java.util.Date;

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

/**
 * A system test class for AddressBook, which provides access to handles of GUI components, and
 * verifies that the starting state of the application is correct.
 */
public abstract class AddressBookSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;

    @BeforeClass
    public static void setupUpBeforeClass() {
        initializeStage();
    }

    @Before
    public void setUp() {
        testApp = setupApplication();
        mainWindowHandle = setupMainWindowHandle();
        verifyApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() throws Exception {
        tearDownStage();
        // i'm not sure whether we can leave this here. it's kinda weird because we extracted the setting of clock
        // into clockRule, but we're leaving it here.
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
     * Checks that the starting state of the application is correct.
     */
    private void verifyApplicationStartingStateIsCorrect() {
        try {
            assertEquals("", getCommandBox().getInput());
            assertEquals("", getResultDisplay().getText());
            assertListMatching(getPersonListPanel(), getTypicalAddressBook().getPersonList());
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

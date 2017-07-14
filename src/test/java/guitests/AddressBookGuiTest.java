package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.StatusBarFooter;

/**
 * A GUI Test class for AddressBook.
 */
public abstract class AddressBookGuiTest {

    protected static Clock originalClock;
    protected static Clock injectedClock;


    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    protected TypicalPersons td = new TypicalPersons();
    protected GuiRobot guiRobot = new GuiRobot();

    protected Stage stage;

    protected MainWindowHandle mainWindowHandle;

    protected TestApp testApp;

    @BeforeClass
    public static void injectFixedClock() {
        originalClock = StatusBarFooter.getClock();
        injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        StatusBarFooter.setClock(injectedClock);
    }

    @AfterClass
    public static void restoreOriginalClock() {
        StatusBarFooter.setClock(originalClock);
    }

    @BeforeClass
    public static void setupOnce() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        FxToolkit.setupApplication(() -> testApp = new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();

        mainWindowHandle = new MainWindowHandle(stage);
        mainWindowHandle.focus();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected AddressBook getInitialData() {
        AddressBook ab = new AddressBook();
        TypicalPersons.loadAddressBookWithSampleData(ab);
        return ab;
    }

    protected CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    protected PersonListPanelHandle getPersonListPanel() {
        return mainWindowHandle.getPersonListPanel();
    }

    protected MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    protected BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    protected StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    protected ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Runs {@code command} in the application's {@code CommandBox}.
     * @return true if the command was executed successfully.
     */
    protected boolean runCommand(String command) {
        return mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Override this in child classes to set the data file location.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws Exception {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the person shown in the card is same as the given person
     */
    protected void assertCardMatchesPerson(PersonCardHandle card, ReadOnlyPerson person) {
        assertTrue(TestUtil.compareCardAndPerson(card, person));
    }

    /**
     * Asserts the size of the person list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfPeople = getPersonListPanel().getNumberOfPeople();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts that only the sync status in the status bar was changed
     * to the {@code injectedCLock} timing, while the save location remains
     * the same.
     */
    protected void assertOnlySyncStatusChanged() {
        String timestamp = new Date(injectedClock.millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, getStatusBarFooter().getSyncStatus());
        getStatusBarFooter().assertSaveLocationNotChanged();
    }

    /**
     * Asserts that the status bar content did not change.
     */
    protected void assertStatusBarUnchanged() {
        getStatusBarFooter().assertSyncStatusNotChanged();
        getStatusBarFooter().assertSaveLocationNotChanged();
    }

    /**
     * Asserts that the content of the storage file matches an {@code AddressBook} with
     * the content same as {@code expectedAddressBook}.
     */
    protected void assertStorageFileContentMatch(AddressBook expectedAddressBook) {
        ReadOnlyAddressBook actualStorage = testApp.readStorageAddressBook();
        assertEquals(expectedAddressBook, actualStorage);
    }

    /**
     * Asserts that the address book in the application's model matches {@code expectedAddressBook}.
     */
    protected void assertModelMatch(AddressBook expectedAddressBook) {
        assertEquals(expectedAddressBook, testApp.getModel().getAddressBook());
    }

    /**
     * Asserts that the person list matches {@code expectedList}.
     */
    protected void assertPersonListPanelMatches(Person[] expectedList) throws PersonNotFoundException {
        assertTrue(getPersonListPanel().isListMatching(expectedList));
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, getResultDisplay().getText());
    }

    /**
     * Asserts that after running the valid command, the model, storage and GUI are all in the correct state.
     */
    protected void assertRunValidCommand(String commandToRun, Person[] expectedList, AddressBook expectedAddressBook,
                                  String expectedResultMessage) throws Exception {

        // ensure that these things do not change
        getBrowserPanel().rememberUrl();
        getStatusBarFooter().rememberSaveLocation();

        runCommand(commandToRun);

        // check that all components are matched
        getBrowserPanel().assertUrlNotChanged();
        assertPersonListPanelMatches(expectedList);
        assertResultMessage(expectedResultMessage);
        assertOnlySyncStatusChanged();
        assertStorageFileContentMatch(expectedAddressBook);
        assertModelMatch(expectedAddressBook);
    }

    protected void raise(BaseEvent event) {
        //JUnit doesn't run its test cases on the UI thread. Platform.runLater is used to post event on the UI thread.
        Platform.runLater(() -> EventsCenter.getInstance().post(event));
    }
}

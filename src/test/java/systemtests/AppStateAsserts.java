package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.time.Clock;
import java.util.Date;

import guitests.GuiRobot;
import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.MainApp;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains assertion methods to verify the state of the application is as expected.
 */
public class AppStateAsserts {

    /**
     * Asserts that after executing the command {@code commandToRun}, the GUI components display what we expected,
     * and the model and storage are modified accordingly.
     */
    public static void assertCommandSuccess(AddressBookSystemTest addressBookSystemTest, String commandToRun,
            Model expectedModel, String expectedResultMessage, boolean browserUrlWillChange,
            boolean personListSelectionWillChange) {

        rememberStates(addressBookSystemTest);
        addressBookSystemTest.runCommand(commandToRun);
        assertComponentsMatchExpected(addressBookSystemTest, true, expectedModel,
                "", expectedResultMessage, browserUrlWillChange, personListSelectionWillChange);
        if (browserUrlWillChange) {
            waitUntilBrowserLoaded(addressBookSystemTest.getBrowserPanel());
        }
    }

    /**
     * Asserts that after executing the command {@code commandToRun}, the GUI components remain unchanged, except for
     * the {@code ResultDisplay} displaying {@code expectedResultMessage}. The model and storage remains unchanged.
     */
    public static void assertCommandFailure(AddressBookSystemTest addressBookSystemTest, String commandToRun,
            String expectedResultMessage) {
        Model expectedModel = new ModelManager(
                new AddressBook(addressBookSystemTest.getTestApp().getModel().getAddressBook()), new UserPrefs());

        rememberStates(addressBookSystemTest);
        addressBookSystemTest.runCommand(commandToRun);
        assertComponentsMatchExpected(addressBookSystemTest, false, expectedModel,
                commandToRun, expectedResultMessage, false, false);
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private static void rememberStates(AddressBookSystemTest addressBookSystemTest) {

        addressBookSystemTest.getBrowserPanel().rememberUrl();
        addressBookSystemTest.getStatusBarFooter().rememberSaveLocation();
        addressBookSystemTest.getStatusBarFooter().rememberSyncStatus();
        addressBookSystemTest.getPersonListPanel().rememberSelectedPersonCard();
    }

    /**
     * Asserts that the GUI components in the application matches what was expected.
     */
    private static void assertComponentsMatchExpected(AddressBookSystemTest addressBookSystemTest,
            boolean addressBookWillUpdate, Model expectedModel, String expectedCommandBoxText,
            String expectedResultMessage, boolean browserUrlWillChange,
            boolean personListSelectionWillChange) {

        assertEquals(expectedCommandBoxText, addressBookSystemTest.getCommandBox().getInput());
        assertEquals(browserUrlWillChange, addressBookSystemTest.getBrowserPanel().isUrlChanged());
        assertListMatching(addressBookSystemTest.getPersonListPanel(),
                expectedModel.getAddressBook().getPersonList().toArray(new ReadOnlyPerson[0]));
        assertEquals(personListSelectionWillChange,
                addressBookSystemTest.getPersonListPanel().isSelectedPersonCardChanged());
        assertEquals(expectedResultMessage, addressBookSystemTest.getResultDisplay().getText());
        assertEquals(expectedModel.getAddressBook(), addressBookSystemTest.getTestApp().readStorageAddressBook());
        assertEquals(expectedModel, addressBookSystemTest.getTestApp().getModel());

        if (addressBookWillUpdate) {
            assertOnlySyncStatusChanged(addressBookSystemTest.getStatusBarFooter(),
                AddressBookSystemTest.INJECTED_CLOCK);
        } else {
            assertStatusBarUnchanged(addressBookSystemTest.getStatusBarFooter());
        }
    }

    /**
     * Sleeps the thread till the {@code browserPanelHandle}'s {@code WebView} is successfully loaded.
     */
    private static void waitUntilBrowserLoaded(BrowserPanelHandle browserPanelHandle) {
        new GuiRobot().waitForEvent(browserPanelHandle::getIsWebViewLoaded);
        browserPanelHandle.setIsWebViewLoaded(false);
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    private static void assertStatusBarUnchanged(StatusBarFooterHandle statusBarFooterHandle) {
        assertFalse(statusBarFooterHandle.isSaveLocationChanged());
        assertFalse(statusBarFooterHandle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of the {@code injectedClock},
     * while the save location remains the same.
     */
    private static void assertOnlySyncStatusChanged(StatusBarFooterHandle statusBarFooterHandle, Clock injectedClock) {
        String timestamp = new Date(injectedClock.millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertFalse(statusBarFooterHandle.isSaveLocationChanged());
    }

    /**
     * Checks that the starting state of the application is correct.
     */
    public static void verifyApplicationStartingStateIsCorrect(AddressBookSystemTest addressBookSystemTest) {
        try {
            assertEquals("", addressBookSystemTest.getCommandBox().getInput());
            assertEquals("", addressBookSystemTest.getResultDisplay().getText());
            assertListMatching(addressBookSystemTest.getPersonListPanel(),
                    getTypicalAddressBook().getPersonList().toArray(new ReadOnlyPerson[0]));
            assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE),
                    addressBookSystemTest.getBrowserPanel().getLoadedUrl());
            assertEquals("./" + addressBookSystemTest.getTestApp().getStorageSaveLocation(),
                    addressBookSystemTest.getStatusBarFooter().getSaveLocation());
            assertEquals(SYNC_STATUS_INITIAL, addressBookSystemTest.getStatusBarFooter().getSyncStatus());
        } catch (Exception e) {
            throw new AssertionError("Starting state is wrong.", e);
        }
    }
}

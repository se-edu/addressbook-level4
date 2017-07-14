package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.time.Clock;
import java.util.Date;

import guitests.guihandles.StatusBarFooterHandle;
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
            Model expectedModel, String expectedResultMessage) throws Exception {
        final String expectedCommandBoxText = "";
        final boolean addressBookWillUpdate = true;

        rememberStates(addressBookSystemTest, addressBookWillUpdate);
        addressBookSystemTest.runCommand(commandToRun);
        assertComponentsMatchesExpected(addressBookSystemTest, addressBookWillUpdate, expectedModel,
                expectedCommandBoxText, expectedResultMessage);
    }

    /**
     * Asserts that after executing the command {@code commandToRun}, the GUI components remain unchanged, except for
     * the {@code ResultDisplay} displaying {@code expectedResultMessage}. The model and storage remains unchanged.
     */
    public static void assertCommandFailure(AddressBookSystemTest addressBookSystemTest, String commandToRun,
            String expectedResultMessage) throws Exception {
        Model expectedModel = new ModelManager(
                new AddressBook(addressBookSystemTest.getTestApp().getModel().getAddressBook()), new UserPrefs());

        final String expectedCommandBoxText = commandToRun;
        final boolean addressBookWillUpdate = false;

        rememberStates(addressBookSystemTest, addressBookWillUpdate);
        addressBookSystemTest.runCommand(commandToRun);
        assertComponentsMatchesExpected(addressBookSystemTest, addressBookWillUpdate, expectedModel,
                expectedCommandBoxText, expectedResultMessage);
    }

    /**
     * Calls {@code BrowserPanelHandle} and {@code StatusBarFooterHandle} to remember their current state,
     * and also remembers the state of the sync status if {@code addressBookWillUpdate} is false.
     */
    private static void rememberStates(AddressBookSystemTest addressBookSystemTest, boolean addressBookWillUpdate)
            throws Exception {

        addressBookSystemTest.getBrowserPanel().rememberUrl();
        addressBookSystemTest.getStatusBarFooter().rememberSaveLocation();

        if (!addressBookWillUpdate) {
            addressBookSystemTest.getStatusBarFooter().rememberSyncStatus();
        }
    }

    /**
     * Asserts that the GUI components in the application matches what was expected.
     */
    private static void assertComponentsMatchesExpected(AddressBookSystemTest addressBookSystemTest,
            boolean addressBookWillUpdate, Model expectedModel, String expectedCommandBoxText,
            String expectedResultMessage) throws Exception {

        assertEquals(expectedCommandBoxText, addressBookSystemTest.getCommandBox().getInput());
        assertFalse(addressBookSystemTest.getBrowserPanel().isUrlChanged());
        assertListMatching(addressBookSystemTest.getPersonListPanel(),
                expectedModel.getAddressBook().getPersonList().toArray(new ReadOnlyPerson[0]));
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
}

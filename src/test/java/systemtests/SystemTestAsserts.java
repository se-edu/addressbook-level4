package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.util.Date;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains assertion methods to check the validity of the application state.
 */
public class SystemTestAsserts {

    /**
     * Asserts that after executing the valid command {@code commandToRun}, the entire application state matches
     * what was expected.
     */
    public static void assertCommandSuccess(AddressBookSystemTest addressBookSystemTest, String commandToRun,
            Model expectedModel, String expectedResultMessage) throws Exception {

        takeSnapshot(addressBookSystemTest, true);
        addressBookSystemTest.runCommand(commandToRun);
        assertComponentsMatchesExpected(addressBookSystemTest, true, expectedModel, expectedResultMessage);
    }

    /**
     * Asserts that after executing the invalid command {@code commandToRun}, the entire application state remains
     * unmodified except for {@code expectedResultMessage}.
     */
    public static void assertCommandFailure(AddressBookSystemTest addressBookSystemTest, String commandToRun,
            String expectedResultMessage) throws Exception {
        Model expectedModel = new ModelManager(
                new AddressBook(addressBookSystemTest.getTestApp().getModel().getAddressBook()), new UserPrefs());

        takeSnapshot(addressBookSystemTest, false);
        addressBookSystemTest.runCommand(commandToRun);
        assertComponentsMatchesExpected(addressBookSystemTest, false, expectedModel, expectedResultMessage);
    }

    /**
     * Calls {@code BrowserPanelHandle} and {@code StatusBarFooterHandle} to save a snapshot of their current state,
     * and only take a snapshot of the sync status if {@code addressBookWillUpdate} is false.
     */
    private static void takeSnapshot(AddressBookSystemTest addressBookSystemTest, boolean addressBookWillUpdate)
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
            boolean addressBookWillUpdate, final Model expectedModel, String expectedResultMessage) throws Exception {

        assertFalse(addressBookSystemTest.getBrowserPanel().isUrlChanged());
        assertTrue(addressBookSystemTest.getPersonListPanel().isListMatching(
                expectedModel.getAddressBook().getPersonList().toArray(new ReadOnlyPerson[0])));
        assertEquals(expectedResultMessage, addressBookSystemTest.getResultDisplay().getText());
        assertEquals(expectedModel.getAddressBook(), addressBookSystemTest.getTestApp().readStorageAddressBook());
        assertEquals(expectedModel, addressBookSystemTest.getTestApp().getModel());

        if (addressBookWillUpdate) {
            assertOnlySyncStatusChanged(addressBookSystemTest.getStatusBarFooter(),
                AddressBookSystemTest.INJECTED_CLOCK);
        } else {
            assertFalse(addressBookSystemTest.getStatusBarFooter().isSaveLocationChanged());
            assertFalse(addressBookSystemTest.getStatusBarFooter().isSyncStatusChanged());
        }
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the {@code injectedClock} timing, while the
     * save location remains the same.
     */
    private static void assertOnlySyncStatusChanged(StatusBarFooterHandle statusBarFooterHandle, Clock injectedClock) {
        String timestamp = new Date(injectedClock.millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertFalse(statusBarFooterHandle.isSaveLocationChanged());
    }
}

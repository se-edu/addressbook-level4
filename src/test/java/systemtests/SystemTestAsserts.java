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
 * Contains assertion methods to check the validity of the application.
 */
public class SystemTestAsserts {

    /**
     * Asserts that only the sync status in the status bar was changed
     * to the {@code injectedClock} timing, while the save location remains
     * the same.
     */
    public static void assertOnlySyncStatusChanged(StatusBarFooterHandle statusBarFooterHandle, Clock injectedClock) {
        String timestamp = new Date(injectedClock.millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertFalse(statusBarFooterHandle.isSaveLocationChanged());
    }

    /**
     * Asserts that after executing the valid command {@code commandToRun}, the entire application state matches
     * what we expected.
     */
    public static void assertRunValidCommand(AddressBookSystemTest addressBookSystemTest, String commandToRun,
            Model expectedModel, String expectedResultMessage) throws Exception {

        // ensure that these things do not change
        addressBookSystemTest.getBrowserPanel().rememberUrl();
        addressBookSystemTest.getStatusBarFooter().rememberSaveLocation();

        addressBookSystemTest.runCommand(commandToRun);

        // check that all components matched
        assertFalse(addressBookSystemTest.getBrowserPanel().isUrlChanged());
        assertTrue(addressBookSystemTest.getPersonListPanel().isListMatching(
                expectedModel.getAddressBook().getPersonList().toArray(new ReadOnlyPerson[0])));
        assertEquals(expectedResultMessage, addressBookSystemTest.getResultDisplay().getText());
        assertOnlySyncStatusChanged(addressBookSystemTest.getStatusBarFooter(),
                AddressBookSystemTest.INJECTED_CLOCK);
        assertEquals(expectedModel.getAddressBook(), addressBookSystemTest.getTestApp().readStorageAddressBook());
        assertEquals(expectedModel, addressBookSystemTest.getTestApp().getModel());
    }

    /**
     * Asserts that after running the invalid command {@code commandToRun}, the entire application state remains
     * unmodified except for {@code expectedResultMessage}.
     */
    public static void assertRunInvalidCommand(AddressBookSystemTest addressBookSystemTest, String commandToRun,
            String expectedResultMessage) throws Exception {
        Model expectedModel = new ModelManager(
                new AddressBook(addressBookSystemTest.getTestApp().getModel().getAddressBook()), new UserPrefs());

        // ensure that these things do not change
        addressBookSystemTest.getBrowserPanel().rememberUrl();
        addressBookSystemTest.getStatusBarFooter().rememberSaveLocation();
        addressBookSystemTest.getStatusBarFooter().rememberSyncStatus();

        addressBookSystemTest.runCommand(commandToRun);

        // check that all components matched
        assertFalse(addressBookSystemTest.getBrowserPanel().isUrlChanged());
        assertTrue(addressBookSystemTest.getPersonListPanel().isListMatching(
                expectedModel.getAddressBook().getPersonList().toArray(new ReadOnlyPerson[0])));
        assertEquals(expectedResultMessage, addressBookSystemTest.getResultDisplay().getText());
        assertFalse(addressBookSystemTest.getStatusBarFooter().isSaveLocationChanged());
        assertFalse(addressBookSystemTest.getStatusBarFooter().isSyncStatusChanged());
        assertEquals(expectedModel.getAddressBook(), addressBookSystemTest.getTestApp().readStorageAddressBook());
        assertEquals(expectedModel, addressBookSystemTest.getTestApp().getModel());
    }
}

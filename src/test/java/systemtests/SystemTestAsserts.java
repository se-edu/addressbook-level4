package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Clock;
import java.util.Date;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.TestApp;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
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
     * Asserts that the application starts with the correct state.
     */
    public static void assertStartStateCorrect(TestApp testApp, StatusBarFooterHandle statusBarFooterHandle,
             BrowserPanelHandle browserPanelHandle, URL defaultBrowserUrl) throws MalformedURLException {
        assertEquals("./" + testApp.getStorageSaveLocation(), statusBarFooterHandle.getSaveLocation());
        assertEquals(defaultBrowserUrl, browserPanelHandle.getLoadedUrl());
    }

    /**
     * Asserts that after running the valid command, the entire application is in the correct state.
     */
    public static void assertRunValidCommand(AddressBookSystemTest addressBookSystemTest, String commandToRun,
            AddressBook expectedAddressBook, Model expectedModel, String expectedResultMessage) throws Exception {

        // ensure that these things do not change
        addressBookSystemTest.getBrowserPanel().rememberUrl();
        addressBookSystemTest.getStatusBarFooter().rememberSaveLocation();

        addressBookSystemTest.runCommand(commandToRun);

        // check that all components are matched
        assertFalse(addressBookSystemTest.getBrowserPanel().isUrlChanged());
        assertTrue(addressBookSystemTest.getPersonListPanel().isListMatching(
                expectedAddressBook.getPersonList().toArray(new ReadOnlyPerson[0])));
        assertEquals(expectedResultMessage, addressBookSystemTest.getResultDisplay().getText());
        assertOnlySyncStatusChanged(addressBookSystemTest.getStatusBarFooter(),
                addressBookSystemTest.getInjectedClock());
        assertEquals(expectedAddressBook, addressBookSystemTest.getTestApp().readStorageAddressBook());
        assertEquals(expectedModel, addressBookSystemTest.getTestApp().getModel());
    }

    /**
     * Asserts that after running the invalid command, no modification were made to the model and the
     * entire applciation is in the wrong command state.
     */
    public static void assertRunInvalidCommand(AddressBookSystemTest addressBookSystemTest, String commandToRun,
            AddressBook expectedAddressBook, Model expectedModel, String expectedResultMessage) throws Exception {

        // ensure that nothing changes
        addressBookSystemTest.getBrowserPanel().rememberUrl();
        addressBookSystemTest.getStatusBarFooter().rememberSaveLocation();
        addressBookSystemTest.getStatusBarFooter().rememberSyncStatus();

        addressBookSystemTest.runCommand(commandToRun);

        // check that no components have been changed
        assertFalse(addressBookSystemTest.getBrowserPanel().isUrlChanged());
        assertTrue(addressBookSystemTest.getPersonListPanel().isListMatching(
                expectedAddressBook.getPersonList().toArray(new ReadOnlyPerson[0])));
        assertEquals(expectedResultMessage, addressBookSystemTest.getResultDisplay().getText());
        assertFalse(addressBookSystemTest.getStatusBarFooter().isSaveLocationChanged());
        assertFalse(addressBookSystemTest.getStatusBarFooter().isSyncStatusChanged());
        assertEquals(expectedAddressBook, addressBookSystemTest.getTestApp().readStorageAddressBook());
        assertEquals(expectedModel, addressBookSystemTest.getTestApp().getModel());
    }
}

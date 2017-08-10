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

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.MainApp;
import seedu.address.TestApp;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains assertion methods to verify the state of the application is as expected.
 */
public class AppStateAsserts {
    /**
     * Asserts that the model in {@code testApp}'s storage and {@code testApp}'s model equals {@code expectedModel}.
     */
    public static void assertAppModel(Model expectedModel, TestApp testApp) {
        assertEquals(expectedModel.getAddressBook(), testApp.readStorageAddressBook());
        assertEquals(expectedModel, testApp.getModel());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    public static void assertStatusBarUnchanged(StatusBarFooterHandle statusBarFooterHandle) {
        assertFalse(statusBarFooterHandle.isSaveLocationChanged());
        assertFalse(statusBarFooterHandle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of the {@code injectedClock},
     * while the save location remains the same.
     */
    public static void assertOnlySyncStatusChanged(StatusBarFooterHandle statusBarFooterHandle, Clock injectedClock) {
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

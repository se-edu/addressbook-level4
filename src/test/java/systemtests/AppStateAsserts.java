package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;
import static systemtests.AddressBookSystemTest.INJECTED_CLOCK;

import java.util.Date;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.MainApp;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains assertion methods to verify the state of the application is as expected.
 */
public class AppStateAsserts {
    private AddressBookSystemTest addressBookSystemTest;

    public AppStateAsserts(AddressBookSystemTest addressBookSystemTest) {
        this.addressBookSystemTest = addressBookSystemTest;
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    public void assertStatusBarUnchanged() {
        StatusBarFooterHandle footer = addressBookSystemTest.getStatusBarFooter();
        assertFalse(footer.isSaveLocationChanged());
        assertFalse(footer.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of the {@code injectedClock},
     * while the save location remains the same.
     */
    public void assertOnlySyncStatusChanged() {
        StatusBarFooterHandle footer = addressBookSystemTest.getStatusBarFooter();
        String timestamp = new Date(INJECTED_CLOCK.millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, footer.getSyncStatus());
        assertFalse(footer.isSaveLocationChanged());
    }

    /**
     * Checks that the starting state of the application is correct.
     */
    public void verifyApplicationStartingStateIsCorrect() {
        StatusBarFooterHandle footer = addressBookSystemTest.getStatusBarFooter();
        try {
            assertEquals("", addressBookSystemTest.getCommandBox().getInput());
            assertEquals("", addressBookSystemTest.getResultDisplay().getText());
            assertListMatching(addressBookSystemTest.getPersonListPanel(),
                    getTypicalAddressBook().getPersonList().toArray(new ReadOnlyPerson[0]));
            assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE),
                    addressBookSystemTest.getBrowserPanel().getLoadedUrl());
            assertEquals("./" + addressBookSystemTest.getTestApp().getStorageSaveLocation(), footer.getSaveLocation());
            assertEquals(SYNC_STATUS_INITIAL, footer.getSyncStatus());
        } catch (Exception e) {
            throw new AssertionError("Starting state is wrong.", e);
        }
    }
}

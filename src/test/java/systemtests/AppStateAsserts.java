package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
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
     * Asserts that the browser's url is changed.
     * @see guitests.guihandles.BrowserPanelHandle#isUrlChanged()
     */
    public void assertBrowserUrlChanged() throws Exception {
        assertTrue(addressBookSystemTest.getBrowserPanel().isUrlChanged());
    }

    /**
     * Asserts that the browser's url remain unchanged.
     * @see guitests.guihandles.BrowserPanelHandle#isUrlChanged()
     */
    public void assertBrowserUrlUnchanged() throws Exception {
        assertFalse(addressBookSystemTest.getBrowserPanel().isUrlChanged());
    }

    /**
     * Asserts that the selected card in the person list panel is changed.
     * @see guitests.guihandles.PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    public void assertSelectedCardChanged() throws Exception {
        assertTrue(addressBookSystemTest.getPersonListPanel().isSelectedPersonCardChanged());
    }

    /**
     * Asserts that the selected card in the person list panel remains unchanged.
     * @see guitests.guihandles.PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    public void assertSelectedCardUnchanged() throws Exception {
        assertFalse(addressBookSystemTest.getPersonListPanel().isSelectedPersonCardChanged());
    }

    /**
     * Asserts that the command box shows {@code expected}.
     */
    public void assertCommandBoxShows(String expected) {
        assertEquals(expected, addressBookSystemTest.getCommandBox().getInput());
    }

    /**
     * Asserts that the result box shows {@code expected}.
     */
    public void assertResultBoxShows(String expected) {
        assertEquals(expected, addressBookSystemTest.getResultDisplay().getText());
    }

    /**
     * Asserts that the address book saved in the storage equals {@code expected}.
     */
    public void assertSavedAddressBookEquals(ReadOnlyAddressBook expected) {
        assertEquals(expected, addressBookSystemTest.getTestApp().getModel().getAddressBook());
    }

    /**
     * Asserts that the current model equals {@code expected}.
     */
    public void assertModelEquals(Model expected) {
        assertEquals(expected, addressBookSystemTest.getTestApp().getModel());
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
     * Asserts that only the sync status in the status bar was changed to the timing of the
     * {@code AddressBookSystemTest#INJECTED_CLOCK}, while the save location remains the same.
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
            assertCommandBoxShows("");
            assertResultBoxShows("");
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

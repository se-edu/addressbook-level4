package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.io.File;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.testutil.TestUtil;
import seedu.address.ui.StatusBarFooter;

public class DeleteCommandTest extends AddressBookGuiTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private DisabledEventStorageManager expectedStorageManager;
    private AddressBook expectedAddressBook;

    private Clock originalClock;
    private Clock injectedClock;

    private void injectFixedClock() {
        originalClock = StatusBarFooter.getClock();
        injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        StatusBarFooter.setClock(injectedClock);
    }

    private void restoreOriginalClock() {
        StatusBarFooter.setClock(originalClock);
    }

    @Before
    public void setUp() throws Exception {
        injectFixedClock();

        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("expectedAb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("expectedPrefs"));
        expectedStorageManager = new DisabledEventStorageManager(addressBookStorage, userPrefsStorage);

        expectedAddressBook = new AddressBook(getInitialData());
        expectedStorageManager.saveAddressBook(expectedAddressBook);
    }

    @After
    public void tearDown() throws Exception {
        restoreOriginalClock();
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + File.separator + fileName;
    }

    @Test
    public void delete() throws Exception {

        //delete the first in the list
        Person[] currentList = td.getTypicalPersons();
        Index targetIndex = INDEX_FIRST_PERSON;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = Index.fromOneBased(currentList.length);
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = Index.fromOneBased(currentList.length / 2);
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " " + currentList.length + 1);
        assertResultMessage("The person index provided is invalid");

    }

    /**
     * Runs the delete command to delete the person at {@code index} and confirms the result is correct.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(Index index, final Person[] currentList) throws Exception {
        Person personToDelete = currentList[index.getZeroBased()];
        Person[] expectedRemainder = TestUtil.removePersonFromList(currentList, index);

        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " " + index.getOneBased());

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(personListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));

        //confirm that the status bar has been changed
        String timestamp = new Date(injectedClock.millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, statusBarFooter.getSyncStatus());
        assertEquals("./" + testApp.getStorageSaveLocation(), statusBarFooter.getSaveLocation());

        //confirm that the file is updated correctly
        expectedAddressBook.removePerson(personToDelete);
        expectedStorageManager.saveAddressBook(expectedAddressBook);
        ReadOnlyAddressBook expectedStorage = new AddressBook(expectedStorageManager.readAddressBook().get());
        ReadOnlyAddressBook actualStorage = testApp.readStorageAddressBook();
        assertEquals(expectedStorage, actualStorage);
    }

}

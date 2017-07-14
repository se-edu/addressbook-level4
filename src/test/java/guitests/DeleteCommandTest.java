package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.util.Date;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.TestUtil;

public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete() throws Exception {
        // assert that the start state is correct
        assertEquals("./" + testApp.getStorageSaveLocation(), getStatusBarFooter().getSaveLocation());

        //delete the first in the list
        Person[] currentList = td.getTypicalPersons();
        Index targetIndex = INDEX_FIRST_PERSON;

        AddressBook expectedAddressBook = td.getTypicalAddressBook();
        expectedAddressBook.removePerson(currentList[targetIndex.getZeroBased()]);
        assertDeleteSuccess(targetIndex, currentList, expectedAddressBook);

        //delete the last in the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = Index.fromOneBased(currentList.length);
        expectedAddressBook.removePerson(currentList[targetIndex.getZeroBased()]);
        assertDeleteSuccess(targetIndex, currentList, expectedAddressBook);

        //delete from the middle of the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = Index.fromOneBased(currentList.length / 2);
        expectedAddressBook.removePerson(currentList[targetIndex.getZeroBased()]);
        assertDeleteSuccess(targetIndex, currentList, expectedAddressBook);

        //invalid index
        runCommand(DeleteCommand.COMMAND_WORD + " " + currentList.length + 1);
        assertResultMessage("The person index provided is invalid");

    }

    /**
     * Runs the delete command to delete the person at {@code index} and confirms the result is correct.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(Index index, final Person[] currentList, AddressBook expectedAddressBook)
            throws Exception {
        Person personToDelete = currentList[index.getZeroBased()];
        Person[] expectedRemainder = TestUtil.removePersonFromList(currentList, index);

        // ensure that these things do not change
        getBrowserPanel().rememberUrl();
        getStatusBarFooter().rememberSaveLocation();

        runCommand(DeleteCommand.COMMAND_WORD + " " + index.getOneBased());

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(getPersonListPanel().isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));

        //confirm that the status bar time changed, but not the save location
        String timestamp = new Date(injectedClock.millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, getStatusBarFooter().getSyncStatus());
        getStatusBarFooter().assertSaveLocationNotChanged();

        //confirm that the browser panel does not change
        getBrowserPanel().assertUrlNotChanged();

        //confirm that the file is updated correctly
        ReadOnlyAddressBook actualStorage = testApp.readStorageAddressBook();
        assertEquals(expectedAddressBook, actualStorage);

        //confirm that the model is correct
        assertEquals(expectedAddressBook, testApp.getModel().getAddressBook());
    }

}

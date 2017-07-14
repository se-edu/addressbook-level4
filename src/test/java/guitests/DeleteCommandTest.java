package guitests;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.TestUtil;

public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete() throws Exception {
        // assert that the start state is correct
        assertEquals("./" + testApp.getStorageSaveLocation(), getStatusBarFooter().getSaveLocation());

        Person[] currentList = td.getTypicalPersons();
        AddressBook currentAddressBook = td.getTypicalAddressBook();

        //delete the first in the list
        Index targetIndex = INDEX_FIRST_PERSON;
        Person personTargeted = currentList[targetIndex.getZeroBased()];
        assertDeleteSuccess(targetIndex, currentList, currentAddressBook);

        //delete the last in the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        currentAddressBook.removePerson(personTargeted);
        targetIndex = Index.fromOneBased(currentList.length);
        personTargeted = currentList[targetIndex.getZeroBased()];
        assertDeleteSuccess(targetIndex, currentList, currentAddressBook);

        //delete from the middle of the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        currentAddressBook.removePerson(personTargeted);
        targetIndex = Index.fromOneBased(currentList.length / 2);
        assertDeleteSuccess(targetIndex, currentList, currentAddressBook);

        //invalid index
        String invalidCommand = DeleteCommand.COMMAND_WORD + " " + currentList.length + 1;
        runCommand(invalidCommand);
        assertResultMessage("The person index provided is invalid");

    }

    /**
     * Runs the delete command to delete the person at {@code index} and confirms the result is correct.
     * @param currentList A copy of the current list of persons (before deletion).
     * @param currentAddressBook A copy of the address book model (before deletion).
     */
    private void assertDeleteSuccess(Index index, final Person[] currentList, final AddressBook currentAddressBook)
            throws Exception {
        String commandToRun = DeleteCommand.COMMAND_WORD + " " + index.getOneBased();

        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, currentList[index.getZeroBased()]);
        Person[] expectedList = TestUtil.removePersonFromList(currentList, index);
        AddressBook expectedAddressBook = new AddressBook(currentAddressBook);
        expectedAddressBook.removePerson(currentList[index.getZeroBased()]);

        assertRunCommand(commandToRun, expectedList, expectedAddressBook, expectedResultMessage);
    }

    /**
     * Asserts that after running the command, the model, storage and GUI are all in the correct state.
     */
    private void assertRunCommand(String commandToRun, Person[] expectedList, AddressBook expectedAddressBook,
                                  String expectedResultMessage) throws Exception {

        // ensure that these things do not change
        getBrowserPanel().rememberUrl();
        getStatusBarFooter().rememberSaveLocation();

        runCommand(commandToRun);

        // check that all components are matched
        getBrowserPanel().assertUrlNotChanged();
        assertPersonListPanelMatches(expectedList);
        assertResultMessage(expectedResultMessage);
        assertOnlySyncStatusChanged();
        assertStorageFileContentMatch(expectedAddressBook);
        assertModelMatch(expectedAddressBook);
    }

}

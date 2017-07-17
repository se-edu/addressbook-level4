package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Person;

public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete() throws Exception {

        //delete the first in the list
        ArrayList<Person> currentList = new ArrayList<>(Arrays.asList(td.getTypicalPersons()));
        Index targetIndex = INDEX_FIRST_PERSON;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        targetIndex = Index.fromOneBased(currentList.size());
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        targetIndex = Index.fromOneBased(currentList.size() / 2);
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        runCommand(DeleteCommand.COMMAND_WORD + " " + currentList.size() + 1);
        assertResultMessage("The person index provided is invalid");

    }

    /**
     * Runs the delete command to delete the person at {@code index} and confirms the result is correct.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(Index index, final List<Person> currentList) throws Exception {
        Person personToDelete = currentList.remove(index.getZeroBased());

        runCommand(DeleteCommand.COMMAND_WORD + " " + index.getOneBased());

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(getPersonListPanel().isListMatching(currentList.toArray(new Person[currentList.size()])));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

}

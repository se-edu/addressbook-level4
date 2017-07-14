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
import seedu.address.model.person.ReadOnlyPerson;

public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete() throws Exception {

        //delete the first in the list
        ArrayList<Person> expectedList = new ArrayList<>(Arrays.asList(td.getTypicalPersons()));
        Index targetIndex = INDEX_FIRST_PERSON;
        expectedList.remove(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, expectedList);

        //delete the last in the list
        targetIndex = Index.fromOneBased(expectedList.size());
        expectedList.remove(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, expectedList);

        //delete from the middle of the list
        targetIndex = Index.fromOneBased(expectedList.size() / 2);
        expectedList.remove(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, expectedList);

        //invalid index
        runCommand(DeleteCommand.COMMAND_WORD + " " + expectedList.size() + 1);
        assertResultMessage("The person index provided is invalid");

    }

    /**
     * Runs the delete command to delete the person at {@code index} and confirms resulting list equals to
     * {@code expectedList} and that the displayed result message is correct.
     */
    private void assertDeleteSuccess(Index index, final List<Person> expectedList) throws Exception {
        ReadOnlyPerson personToDelete = getPersonListPanel().getCard(index.getZeroBased()).person;

        runCommand(DeleteCommand.COMMAND_WORD + " " + index.getOneBased());

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(getPersonListPanel().isListMatching(expectedList.toArray(new Person[expectedList.size()])));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }
}

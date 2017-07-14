package systemtests;

import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static systemtests.SystemTestAsserts.assertRunInvalidCommand;
import static systemtests.SystemTestAsserts.assertRunValidCommand;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class DeleteCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void delete() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());

        //delete the first in the list
        Index targetIndex = INDEX_FIRST_PERSON;
        ReadOnlyPerson targetPerson = expectedModel.getAddressBook().getPersonList().get(targetIndex.getZeroBased());
        expectedModel.deletePerson(targetPerson);
        assertDeleteSuccess(targetIndex, targetPerson, expectedModel);

        //delete the last in the list
        targetIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size());
        targetPerson = expectedModel.getAddressBook().getPersonList().get(targetIndex.getZeroBased());
        expectedModel.deletePerson(targetPerson);
        assertDeleteSuccess(targetIndex, targetPerson, expectedModel);

        //delete from the middle of the list
        targetIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size() / 2);
        targetPerson = expectedModel.getAddressBook().getPersonList().get(targetIndex.getZeroBased());
        expectedModel.deletePerson(targetPerson);
        assertDeleteSuccess(targetIndex, targetPerson, expectedModel);

        //invalid index
        String invalidCommand = DeleteCommand.COMMAND_WORD + " "
                + expectedModel.getAddressBook().getPersonList().size() + 1;
        assertDeleteFailure(invalidCommand, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // invalid arguments
        invalidCommand = DeleteCommand.COMMAND_WORD + " abc";
        assertDeleteFailure(invalidCommand,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        invalidCommand = DeleteCommand.COMMAND_WORD + " 1 abc";
        assertDeleteFailure(invalidCommand,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Runs the delete command to delete {@code targetPerson} at {@code index} and confirms the entire application
     * state matches what we expected.
     * @param expectedModel A copy of the address book model (after deletion).
     */
    private void assertDeleteSuccess(Index index, final ReadOnlyPerson targetPerson, final Model expectedModel)
            throws Exception {
        String commandToRun = DeleteCommand.COMMAND_WORD + " " + index.getOneBased();
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);

        assertRunValidCommand(this, commandToRun, expectedModel, expectedResultMessage);
    }

    /**
     * Runs the {@code invalidCommand}, and confirms the entire application state matches what we expected.
     */
    private void assertDeleteFailure(String invalidCommand, String expectedResultMessage) throws Exception {
        assertRunInvalidCommand(this, invalidCommand, expectedResultMessage);
    }
}

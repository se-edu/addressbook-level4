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
        Model currentModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());

        //delete the first in the list
        Index targetIndex = INDEX_FIRST_PERSON;
        ReadOnlyPerson personTargeted = currentModel.getAddressBook().getPersonList().get(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, currentModel);

        //delete the last in the list
        currentModel.deletePerson(personTargeted);
        targetIndex = Index.fromOneBased(currentModel.getAddressBook().getPersonList().size());
        personTargeted = currentModel.getAddressBook().getPersonList().get(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, currentModel);

        //delete from the middle of the list
        currentModel.deletePerson(personTargeted);
        targetIndex = Index.fromOneBased(currentModel.getAddressBook().getPersonList().size() / 2);
        personTargeted = currentModel.getAddressBook().getPersonList().get(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, currentModel);

        //invalid index
        currentModel.deletePerson(personTargeted);
        String invalidCommand = DeleteCommand.COMMAND_WORD + " "
                + currentModel.getAddressBook().getPersonList().size() + 1;
        assertDeleteFailure(invalidCommand, currentModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // invalid arguments
        invalidCommand = DeleteCommand.COMMAND_WORD + " abc";
        assertDeleteFailure(invalidCommand, currentModel,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        invalidCommand = DeleteCommand.COMMAND_WORD + " 1 abc";
        assertDeleteFailure(invalidCommand, currentModel,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Runs the delete command to delete the person at {@code index} and confirms the result is correct.
     * @param currentModel A copy of the address book model (before deletion).
     */
    private void assertDeleteSuccess(Index index, final Model currentModel)
            throws Exception {
        String commandToRun = DeleteCommand.COMMAND_WORD + " " + index.getOneBased();

        ReadOnlyPerson targetedPerson = currentModel.getAddressBook().getPersonList().get(index.getZeroBased());
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetedPerson);

        AddressBook expectedAddressBook = new AddressBook(currentModel.getAddressBook());
        expectedAddressBook.removePerson(targetedPerson);

        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());

        assertRunValidCommand(this, commandToRun, expectedModel, expectedResultMessage);
    }

    /**
     * Runs the delete command that is malformed, and confirms the result is correct.
     */
    private void assertDeleteFailure(String invalidCommand, final Model currentModel,
            String expectedResultMessage) throws Exception {
        AddressBook expectedAddressBook = new AddressBook(currentModel.getAddressBook());
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());

        assertRunInvalidCommand(this, invalidCommand, expectedModel, expectedResultMessage);
    }
}

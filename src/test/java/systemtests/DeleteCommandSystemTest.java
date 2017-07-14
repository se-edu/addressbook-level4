package systemtests;

import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static systemtests.AppStateAsserts.assertCommandFailure;
import static systemtests.AppStateAsserts.assertCommandSuccess;

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

    private static final String INVALID_DELETE_COMMAND_FORMAT_MESSAGE =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    private Model expectedModel;

    @Test
    public void delete() throws Exception {
        expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()), new UserPrefs());

        // delete the first in the list
        assertDeleteSuccess(INDEX_FIRST_PERSON);

        // delete the last in the list
        Index lastPersonIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size());
        assertDeleteSuccess(lastPersonIndex);

        // delete from the middle of the list
        Index middlePersonIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size() / 2);
        assertDeleteSuccess(middlePersonIndex);

        // invalid index
        Index outOfBoundsIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size() + 1);
        assertDeleteFailure(outOfBoundsIndex);

        // invalid arguments
        assertDeleteFailure(" abc");
        assertDeleteFailure(" 1 abc");
    }

    /**
     * Asserts that after executing the delete command with {@code validIndex}, the model updates, and the entire
     * application state matches what was expected.
     */
    private void assertDeleteSuccess(Index validIndex) throws Exception {
        ReadOnlyPerson targetPerson = expectedModel.getAddressBook().getPersonList().get(validIndex.getZeroBased());
        expectedModel.deletePerson(targetPerson);

        String command = DeleteCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);

        assertCommandSuccess(this, command, expectedModel, expectedResultMessage);
    }

    /**
     * Asserts that after executing the delete command with {@code invalidIndex}, the GUI remains the same with the
     * model and storage unchanged as well, but the {@code ResultDisplay} should show
     * {@code Messages#MESSAGE_INVALID_PERSON_DISPLAYED_INDEX}.
     */
    private void assertDeleteFailure(Index invalidIndex) throws Exception {
        String invalidCommand = DeleteCommand.COMMAND_WORD + " " + invalidIndex.getOneBased();
        String expectedResultMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        assertCommandFailure(this, invalidCommand, expectedResultMessage);
    }

    /**
     * Asserts that after executing the delete command with {@code invalidArguments}, the GUI remains the same with the
     * model and storage unchanged as well, but the {@code ResultDisplay} should show
     * {@code #INVALID_DELETE_COMMAND_FORMAT_MESSAGE}.
     */
    private void assertDeleteFailure(String invalidArguments) throws Exception {
        String invalidCommand = DeleteCommand.COMMAND_WORD + " " + invalidArguments;
        String expectedResultMessage = INVALID_DELETE_COMMAND_FORMAT_MESSAGE;

        assertCommandFailure(this, invalidCommand, expectedResultMessage);
    }

}

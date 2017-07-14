package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
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
        ReadOnlyPerson targetPerson = expectedModel.getAddressBook().getPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        String command = String.valueOf(INDEX_FIRST_PERSON.getOneBased());
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);
        expectedModel.deletePerson(targetPerson);
        assertDeleteSuccess(command, expectedResultMessage);

        // delete the last in the list
        Index lastPersonIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size());
        targetPerson = expectedModel.getAddressBook().getPersonList().get(lastPersonIndex.getZeroBased());
        command = String.valueOf(lastPersonIndex.getOneBased());
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);
        expectedModel.deletePerson(targetPerson);
        assertDeleteSuccess(command, expectedResultMessage);

        // delete from the middle of the list
        Index middlePersonIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size() / 2);
        targetPerson = expectedModel.getAddressBook().getPersonList().get(middlePersonIndex.getZeroBased());
        command = String.valueOf(middlePersonIndex.getOneBased());
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);
        expectedModel.deletePerson(targetPerson);
        assertDeleteSuccess(command, expectedResultMessage);

        // invalid index
        Index outOfBoundsIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size() + 1);
        assertDeleteFailure(String.valueOf(outOfBoundsIndex.getOneBased()), MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // invalid arguments
        assertDeleteFailure("abc", INVALID_DELETE_COMMAND_FORMAT_MESSAGE);
        assertDeleteFailure("1 abc", INVALID_DELETE_COMMAND_FORMAT_MESSAGE);
    }

    /**
     * Asserts that after executing the delete command with {@code validArguments}, the model and storage are updated,
     * and the {@code ResultDisplay} shows {@code expectedResultMessage}.
     */
    private void assertDeleteSuccess(String validArguments, String expectedResultMessage) throws Exception {
        String validCommand = DeleteCommand.COMMAND_WORD + " " + validArguments;
        assertCommandSuccess(this, validCommand, expectedModel, expectedResultMessage);
    }

    /**
     * Asserts that after executing the delete command with {@code invalidArguments}, the GUI remains the same with the
     * model and storage unchanged as well, but the {@code ResultDisplay} should show {@code expectedResultMessage}.
     */
    private void assertDeleteFailure(String invalidArguments, String expectedResultMessage) throws Exception {
        String invalidCommand = DeleteCommand.COMMAND_WORD + " " + invalidArguments;
        assertCommandFailure(this, invalidCommand, expectedResultMessage);
    }
}

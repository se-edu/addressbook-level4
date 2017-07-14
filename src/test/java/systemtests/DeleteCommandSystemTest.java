package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getPerson;
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

    @Test
    public void delete() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());

        // delete the first in the list
        ReadOnlyPerson targetPerson = getPerson(expectedModel, INDEX_FIRST_PERSON);
        String command = getUserInput(String.valueOf(INDEX_FIRST_PERSON.getOneBased()));
        String expectedResultMessage = getSuccessMessage(targetPerson);
        expectedModel.deletePerson(targetPerson);
        assertCommandSuccess(this, command, expectedModel, expectedResultMessage);

        // delete the last in the list
        Index lastPersonIndex = getLastIndex(expectedModel);
        targetPerson = getPerson(expectedModel, lastPersonIndex);
        command = getUserInput(String.valueOf(lastPersonIndex.getOneBased()));
        expectedResultMessage = getSuccessMessage(targetPerson);
        expectedModel.deletePerson(targetPerson);
        assertCommandSuccess(this, command, expectedModel, expectedResultMessage);

        // delete from the middle of the list
        Index middlePersonIndex = getMidIndex(expectedModel);
        targetPerson = getPerson(expectedModel, middlePersonIndex);
        command = getUserInput(String.valueOf(middlePersonIndex.getOneBased()));
        expectedResultMessage = getSuccessMessage(targetPerson);
        expectedModel.deletePerson(targetPerson);
        assertCommandSuccess(this, command, expectedModel, expectedResultMessage);

        // invalid index
        Index outOfBoundsIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size() + 1);
        command = getUserInput(String.valueOf(outOfBoundsIndex.getOneBased()));
        assertCommandFailure(this, command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // invalid arguments
        assertCommandFailure(this,
                DeleteCommand.COMMAND_WORD + " abc", INVALID_DELETE_COMMAND_FORMAT_MESSAGE);
        assertCommandFailure(this,
                DeleteCommand.COMMAND_WORD + " 1 abc", INVALID_DELETE_COMMAND_FORMAT_MESSAGE);
    }

    private String getUserInput(String arguments) {
        return DeleteCommand.COMMAND_WORD + " " + arguments;
    }

    private String getSuccessMessage(ReadOnlyPerson person) {
        return String.format(MESSAGE_DELETE_PERSON_SUCCESS, person);
    }
}

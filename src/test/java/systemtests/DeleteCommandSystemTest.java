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
        String validCommand = DeleteCommand.COMMAND_WORD + " " + targetIndex.getOneBased();
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);

        assertRunValidCommand(this, validCommand, expectedModel, expectedResultMessage);

        //delete the last in the list
        targetIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size());
        targetPerson = expectedModel.getAddressBook().getPersonList().get(targetIndex.getZeroBased());
        expectedModel.deletePerson(targetPerson);
        validCommand = DeleteCommand.COMMAND_WORD + " " + targetIndex.getOneBased();
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);

        assertRunValidCommand(this, validCommand, expectedModel, expectedResultMessage);

        //delete from the middle of the list
        targetIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size() / 2);
        targetPerson = expectedModel.getAddressBook().getPersonList().get(targetIndex.getZeroBased());
        expectedModel.deletePerson(targetPerson);
        validCommand = DeleteCommand.COMMAND_WORD + " " + targetIndex.getOneBased();
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);

        assertRunValidCommand(this, validCommand, expectedModel, expectedResultMessage);

        //invalid index
        String invalidCommand = DeleteCommand.COMMAND_WORD + " "
                + expectedModel.getAddressBook().getPersonList().size() + 1;
        expectedResultMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertRunInvalidCommand(this, invalidCommand, expectedResultMessage);

        // invalid arguments
        invalidCommand = DeleteCommand.COMMAND_WORD + " abc";
        expectedResultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertRunInvalidCommand(this, invalidCommand, expectedResultMessage);
        invalidCommand = DeleteCommand.COMMAND_WORD + " 1 abc";
        assertRunInvalidCommand(this, invalidCommand, expectedResultMessage);
    }

}

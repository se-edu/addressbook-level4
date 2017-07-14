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
        AddressBook currentAddressBook = td.getTypicalAddressBook();

        //delete the first in the list
        Index targetIndex = INDEX_FIRST_PERSON;
        ReadOnlyPerson personTargeted = currentAddressBook.getPersonList().get(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, currentAddressBook);

        //delete the last in the list
        currentAddressBook.removePerson(personTargeted);
        targetIndex = Index.fromOneBased(currentAddressBook.getPersonList().size());
        personTargeted = currentAddressBook.getPersonList().get(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, currentAddressBook);

        //delete from the middle of the list
        currentAddressBook.removePerson(personTargeted);
        targetIndex = Index.fromOneBased(currentAddressBook.getPersonList().size() / 2);
        personTargeted = currentAddressBook.getPersonList().get(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, currentAddressBook);

        //invalid index
        currentAddressBook.removePerson(personTargeted);
        String invalidCommand = DeleteCommand.COMMAND_WORD + " " + currentAddressBook.getPersonList().size() + 1;
        assertDeleteFailure(invalidCommand, currentAddressBook, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // invalid arguments
        invalidCommand = DeleteCommand.COMMAND_WORD + " abc";
        assertDeleteFailure(invalidCommand, currentAddressBook,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        invalidCommand = DeleteCommand.COMMAND_WORD + " 1 abc";
        assertDeleteFailure(invalidCommand, currentAddressBook,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Runs the delete command to delete the person at {@code index} and confirms the result is correct.
     * @param currentAddressBook A copy of the address book model (before deletion).
     */
    private void assertDeleteSuccess(Index index, final AddressBook currentAddressBook)
            throws Exception {
        String commandToRun = DeleteCommand.COMMAND_WORD + " " + index.getOneBased();

        ReadOnlyPerson targetedPerson = currentAddressBook.getPersonList().get(index.getZeroBased());
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetedPerson);

        AddressBook expectedAddressBook = new AddressBook(currentAddressBook);
        expectedAddressBook.removePerson(targetedPerson);

        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());

        assertRunValidCommand(this, commandToRun, expectedAddressBook, expectedModel,
                expectedResultMessage);
    }

    /**
     * Runs the delete command that is malformed, and confirms the result is correct.
     */
    private void assertDeleteFailure(String invalidCommand, final AddressBook currentAddressBook,
            String expectedResultMessage) throws Exception {
        AddressBook expectedAddressBook = new AddressBook(currentAddressBook);
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());

        assertRunInvalidCommand(this, invalidCommand, expectedAddressBook, expectedModel,
                expectedResultMessage);
    }
}

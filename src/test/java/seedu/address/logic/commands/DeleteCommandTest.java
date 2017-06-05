package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import org.junit.Test;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteCommand.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validCommand_succeeds() throws Exception {
        ReadOnlyPerson deletedPerson = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.removePerson(deletedPerson);
        FilteredList<ReadOnlyPerson> expectedFilteredList = new FilteredList<>(expectedAddressBook.getPersonList());

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedAddressBook,
                expectedFilteredList);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredPersonList().size());
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);
        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns an {@code DeleteCommand} with the parameter {@code index}
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory());
        return deleteCommand;
    }
}

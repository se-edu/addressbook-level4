package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

import java.util.List;

import org.junit.Test;

import javafx.collections.transformation.FilteredList;
import seedu.address.logic.History;
import seedu.address.logic.HistoryManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

public class ReversibleCommandTest {
    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
    private History history = new HistoryManager();

    @Test
    public void rollback_validCommand_succeeds() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(0);
        deleteCommand.setData(model, history);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        ReadOnlyPerson toRemove = model.getFilteredPersonList().get(0);
        expectedAddressBook.removePerson(toRemove);

        String expectedMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, toRemove);
        FilteredList<ReadOnlyPerson> expectedFilteredList = new FilteredList<>(expectedAddressBook.getPersonList());

        assertCommandSuccess(deleteCommand, expectedMessage, expectedAddressBook, expectedFilteredList);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the address book in the model matches {@code expectedAddressBook} <br>
     * - the filtered person list in the model matches {@code expectedFilteredList} <br>
     */
    private void assertCommandSuccess(Command command, String expectedMessage,
                                      ReadOnlyAddressBook expectedAddressBook,
                                      List<? extends ReadOnlyPerson> expectedFilteredList) throws CommandException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedFilteredList, model.getFilteredPersonList());
    }
}

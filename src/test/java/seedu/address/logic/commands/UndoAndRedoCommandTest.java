package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

public class UndoAndRedoCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private History history;

    private static DeleteCommand deleteCommand;
    private static UndoCommand undoCommand;
    private static RedoCommand redoCommand;

    @Before
    public void setUpBeforeEachTest() throws Exception {
        model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
        history = new HistoryManager();
        deleteCommand.setData(model, history);
        undoCommand.setData(model, history);
        redoCommand.setData(model, history);
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        deleteCommand = new DeleteCommand(1);
        undoCommand = new UndoCommand();
        redoCommand = new RedoCommand();
    }

    @Test
    public void undoCommandExecute_noCommandInHistory_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(UndoCommand.MESSAGE_FAILURE);

        undoCommand.execute();
    }

    @Test
    public void redoCommandExecute_noCommandInHistory_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(RedoCommand.MESSAGE_FAILURE);

        redoCommand.execute();
    }

    @Test
    public void undoAndRedoCommandExecute_oneCommandInHistory_success() throws Exception {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        FilteredList<ReadOnlyPerson> expectedFilteredList = new FilteredList<>(expectedAddressBook.getPersonList());

        deleteCommand.execute();
        history.addNewExecutedCommand(deleteCommand);

        updateFilteredListShowOnlyFirstPersonInAddressBook();

        // expectedAddressBook should remain the same as it was in the beginning
        // and expectedFilteredList should show all people
        assertCommandSuccess(undoCommand, UndoCommand.MESSAGE_SUCCESS, expectedAddressBook, expectedFilteredList);

        ReadOnlyPerson toDelete = expectedFilteredList.get(0);
        expectedAddressBook.removePerson(toDelete);

        assertCommandSuccess(redoCommand, RedoCommand.MESSAGE_SUCCESS, expectedAddressBook, expectedFilteredList);
    }

    private void updateFilteredListShowOnlyFirstPersonInAddressBook() {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new HashSet<>(Collections.singletonList(splitName[0])));
        assertTrue(model.getFilteredPersonList().size() == 1);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the address book in the model matches {@code expectedAddressBook} <br>
     * - the filtered person list in the model matches
     * {@code expectedFilteredList} <br>
     */
    private void assertCommandSuccess(Command command, String expectedMessage, ReadOnlyAddressBook expectedAddressBook,
            List<? extends ReadOnlyPerson> expectedFilteredList) throws CommandException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedFilteredList, model.getFilteredPersonList());
    }
}

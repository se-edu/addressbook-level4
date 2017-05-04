package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * The methods in HistoryManager cannot be tested independently. For HistoryManager#addNewExecutedCommand(Command)
 * to be tested, both HistoryManager#undoPreviousReversibleCommand and HistoryManager#executeNextReversibleCommand
 * need to be executed as well. For HistoryManager#executeNextReversibleCommand to be tested,
 * HistoryManager#undoPreviousReversibleCommand needs to be executed which requires
 * HistoryManager#addNewExecutedCommand(Command) to be executed.
 *
 * As such, when testing for each method, we have to assume that other methods are working as intended. Also, we have
 * to assume that the commands used in the tests are working as intended.
 */
public class HistoryManagerTest {
    private Model model;
    private History history;

    private static Person toAdd;
    private static List<Command> standardCommandList;

    // Commands
    private static AddCommand reversibleAddCommand;
    private static ClearCommand reversibleClearCommand;
    private static ListCommand nonReversibleListCommand;
    private static SelectCommand nonReversibleSelectCommand;

    @Before
    public void setUpBeforeEachTest() throws Exception {
        model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
        history = new HistoryManager();

        reversibleAddCommand.setData(model, history);
        reversibleClearCommand.setData(model, history);
        nonReversibleListCommand.setData(model, history);
        nonReversibleSelectCommand.setData(model, history);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        toAdd = new PersonBuilder().build();
        reversibleAddCommand = new AddCommand(toAdd);
        reversibleClearCommand = new ClearCommand();
        nonReversibleListCommand = new ListCommand();
        nonReversibleSelectCommand = new SelectCommand(1);

        standardCommandList = new ArrayList<>();
        standardCommandList.add(reversibleAddCommand);
        standardCommandList.add(nonReversibleSelectCommand);
        standardCommandList.add(nonReversibleListCommand);
        standardCommandList.add(reversibleClearCommand);
        standardCommandList.add(nonReversibleListCommand);
    }


    @Test
    public void addNewExecutedCommand_addSingleReversibleCommand_success() throws Exception {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        FilteredList<ReadOnlyPerson> expectedFilteredList = new FilteredList<>(expectedAddressBook.getPersonList());

        reversibleAddCommand.execute();
        history.addNewExecutedCommand(reversibleAddCommand);
        history.undoPreviousReversibleCommand();

        // expectedAddressBook and expectedFilteredList should not change
        assertCorrectResult(expectedAddressBook, expectedFilteredList);
    }

    @Test
    public void addNewExecutedCommand_clearRedoStack_throwsCommandException() throws Exception {
        for (Command command : standardCommandList) {
            command.execute();
            history.addNewExecutedCommand(command);
        }

        // undo populates redoStack
        history.undoPreviousReversibleCommand();
        history.undoPreviousReversibleCommand();

        nonReversibleListCommand.execute();
        history.addNewExecutedCommand(nonReversibleListCommand); // expected to clear redoStack

        assertRedoFailure();
    }

    @Test
    public void undoPreviousReversibleCommand_emptyUndoStack_throwsCommandException() throws Exception {
        assertUndoFailure();
    }

    @Test
    public void undoPreviousReversibleCommand_onlyNonReversibleCommands_throwsCommandException() throws Exception {
        nonReversibleListCommand.execute();
        history.addNewExecutedCommand(nonReversibleListCommand);
        nonReversibleSelectCommand.execute();
        history.addNewExecutedCommand(nonReversibleSelectCommand);

        assertUndoFailure();
    }

    @Test
    public void executeNextReversibleCommand_emptyRedoStack_throwsCommandException() throws Exception {
        assertRedoFailure();
    }

    @Test
    public void undoPreviousReversibleCommandAndExecuteNextReversibleCommand_nonReversibleAndReversibleCommands() throws
            Exception {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        FilteredList<ReadOnlyPerson> expectedFilteredList = new FilteredList<>(expectedAddressBook.getPersonList());

        for (Command command : standardCommandList) {
            command.execute();
            history.addNewExecutedCommand(command);
        }

        expectedAddressBook.addPerson(toAdd);
        history.undoPreviousReversibleCommand(); // expected to undo ClearCommand
        assertCorrectResult(expectedAddressBook, expectedFilteredList);

        expectedAddressBook.removePerson(toAdd);
        history.undoPreviousReversibleCommand(); // expected to undo AddCommand
        assertCorrectResult(expectedAddressBook, expectedFilteredList);

        assertUndoFailure();

        expectedAddressBook.addPerson(toAdd);
        history.executeNextReversibleCommand(); // expected to redo AddCommand
        assertCorrectResult(expectedAddressBook, expectedFilteredList);

        expectedAddressBook.resetData(new AddressBook());
        history.executeNextReversibleCommand(); // expected to redo ClearCommand
        assertCorrectResult(expectedAddressBook, expectedFilteredList);
    }

    /**
     * Confirms that <br>
     * - the address book in the model matches {@code expectedAddressBook} <br>
     * - the filtered person list in the model matches {@code expectedFilteredList} <br>
     */
    private void assertCorrectResult(ReadOnlyAddressBook expectedAddressBook,
                                      List<? extends ReadOnlyPerson> expectedFilteredList) throws CommandException {
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedFilteredList, model.getFilteredPersonList());
    }

    /**
     * Confirms that execution of {@code HistoryManager#undoPreviousReversibleCommand} fails and the error message
     * equals to {@value UndoCommand#MESSAGE_FAILURE}
     */
    private void assertUndoFailure() {
        try {
            history.undoPreviousReversibleCommand();
            fail();
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), UndoCommand.MESSAGE_FAILURE);
        }
    }

    /**
     * Confirms that execution of {@code HistoryManager#executeNextReversibleCommand} fails and the error message
     * equals to {@value RedoCommand#MESSAGE_FAILURE}
     */
    private void assertRedoFailure() {
        try {
            history.executeNextReversibleCommand();
            fail();
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), RedoCommand.MESSAGE_FAILURE);
        }
    }
}

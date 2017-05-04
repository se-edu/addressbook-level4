package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.OutOfReversibleCommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * The methods in ReversibleCommandHistory cannot be tested independently. For ReversibleCommandHistory#add(Command)
 * to be tested, both ReversibleCommandHistory#previous() and ReversibleCommandHistory#next() need to be executed
 * as well. For ReversibleCommandHistory#next() to be tested, ReversibleCommandHistory#previous() needs to be
 * executed which requires ReversibleCommandHistory#add(Command) to be executed.
 *
 * As such, when testing for each method, we have to assume that other methods are working as intended.
 */
public class ReversibleCommandHistoryTest {
    private static List<Command> standardCommandList;

    // Commands
    private static AddCommand reversibleAddCommand;
    private static ClearCommand reversibleClearCommand;
    private static ListCommand nonReversibleListCommand;
    private static SelectCommand nonReversibleSelectCommand;

    private ReversibleCommandHistory reversibleCommandHistory;

    @Before
    public void setUpBeforeEachTest() throws Exception {
        Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();
        reversibleCommandHistory = new ReversibleCommandHistory();

        reversibleAddCommand.setData(model, commandHistory, reversibleCommandHistory);
        reversibleClearCommand.setData(model, commandHistory, reversibleCommandHistory);
        nonReversibleListCommand.setData(model, commandHistory, reversibleCommandHistory);
        nonReversibleSelectCommand.setData(model, commandHistory, reversibleCommandHistory);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Person toAdd = new PersonBuilder().build();
        reversibleAddCommand = new AddCommand(toAdd);
        reversibleClearCommand = new ClearCommand();
        nonReversibleListCommand = new ListCommand();
        nonReversibleSelectCommand = new SelectCommand(INDEX_FIRST_PERSON);

        standardCommandList = new ArrayList<>();
        standardCommandList.add(reversibleAddCommand);
        standardCommandList.add(nonReversibleSelectCommand);
        standardCommandList.add(nonReversibleListCommand);
        standardCommandList.add(reversibleClearCommand);
        standardCommandList.add(nonReversibleListCommand);
    }

    @Test
    public void add_clearRedoStack_throwsCommandException() throws Exception {
        standardCommandList.forEach(reversibleCommandHistory::add);

        // previous() populates redoStack
        reversibleCommandHistory.previous();
        reversibleCommandHistory.previous();

        reversibleCommandHistory.add(nonReversibleListCommand); // add() expected to clear redoStack

        assertNextFailure();

        assertPreviousFailure();
    }

    @Test
    public void add_addUndo_redoStackUncleared() throws Exception {
        standardCommandList.forEach(reversibleCommandHistory::add);

        // previous() populates redoStack
        reversibleCommandHistory.previous();
        reversibleCommandHistory.previous();

        reversibleCommandHistory.add(new UndoCommand()); // adding UndoCommand not expected to clear redoStack

        assertPreviousFailure();
        // calling next() to ensure OutOfReversibleCommandException is not thrown
        reversibleCommandHistory.next();
        reversibleCommandHistory.next();
    }

    @Test
    public void add_addRedo_redoStackUncleared() throws Exception {
        standardCommandList.forEach(reversibleCommandHistory::add);

        // previous() populates redoStack
        reversibleCommandHistory.previous();
        reversibleCommandHistory.previous();

        reversibleCommandHistory.add(new RedoCommand()); // adding RedoCommand not expected to clear redoStack

        assertPreviousFailure();
        // calling next() to ensure OutOfReversibleCommandException is not thrown
        reversibleCommandHistory.next();
        reversibleCommandHistory.next();
    }

    @Test
    public void previousAndNext_emptyUndoStack_throwsCommandException() {
        assertPreviousFailure();
        assertNextFailure();
    }

    @Test
    public void previousAndNext_onlyNonReversibleCommands_throwsCommandException() {
        reversibleCommandHistory.add(nonReversibleListCommand);
        reversibleCommandHistory.add(nonReversibleSelectCommand);

        assertPreviousFailure();
        assertNextFailure();
    }

    @Test
    public void previousAndNext_nonReversibleAndReversibleCommands() throws Exception {
        standardCommandList.forEach(reversibleCommandHistory::add);

        assertEquals(reversibleClearCommand, reversibleCommandHistory.previous());
        assertEquals(reversibleAddCommand, reversibleCommandHistory.previous());
        assertPreviousFailure();

        assertEquals(reversibleAddCommand, reversibleCommandHistory.next());
        assertEquals(reversibleClearCommand, reversibleCommandHistory.next());
        assertNextFailure();
    }

    /**
     * Confirms that execution of {@code ReversibleCommandHistory#previous()} fails and
     * {@code OutOfReversibleCommandException} is thrown and the error message equals
     * to {@value UndoCommand#MESSAGE_FAILURE}.
     */
    private void assertPreviousFailure() {
        try {
            reversibleCommandHistory.previous();
            fail();
        } catch (OutOfReversibleCommandException ce) {
            assertEquals(ce.getMessage(), UndoCommand.MESSAGE_FAILURE);
        }
    }

    /**
     * Confirms that execution of {@code ReversibleCommandHistory#next()} fails and
     * and {@code OutOfReversibleCommandException} is thrown and the error message
     * equals to {@value RedoCommand#MESSAGE_FAILURE}.
     */
    private void assertNextFailure() {
        try {
            reversibleCommandHistory.next();
            fail();
        } catch (OutOfReversibleCommandException ce) {
            assertEquals(ce.getMessage(), RedoCommand.MESSAGE_FAILURE);
        }
    }
}

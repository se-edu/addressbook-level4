package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import java.util.Arrays;
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
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class UndoRedoStackTest {
    private static List<Command> standardCommandList;

    // Commands
    private static AddCommand reversibleAddCommand;
    private static ClearCommand reversibleClearCommand;
    private static ListCommand nonReversibleListCommand;
    private static SelectCommand nonReversibleSelectCommand;

    private UndoRedoStack undoRedoStack;

    @Before
    public void setUpBeforeEachTest() throws Exception {
        Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
        CommandHistory history = new CommandHistory();
        undoRedoStack = new UndoRedoStack();

        reversibleAddCommand.setData(model, history);
        reversibleClearCommand.setData(model, history);
        nonReversibleListCommand.setData(model, history);
        nonReversibleSelectCommand.setData(model, history);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        reversibleAddCommand = new AddCommand(new PersonBuilder().build());
        reversibleClearCommand = new ClearCommand();
        nonReversibleListCommand = new ListCommand();
        nonReversibleSelectCommand = new SelectCommand(INDEX_FIRST_PERSON);

        standardCommandList = Arrays.asList(reversibleAddCommand, nonReversibleSelectCommand, nonReversibleListCommand,
                reversibleClearCommand, nonReversibleListCommand);
    }

    @Test
    public void add_addNonReversibleCommand_redoStackCleared() throws Exception {
        standardCommandList.forEach(undoRedoStack::add);

        // previous() populates redoStack
        undoRedoStack.previous();
        undoRedoStack.previous();

        undoRedoStack.add(nonReversibleListCommand); // add() expected to clear redoStack
        assertEquals(new UndoRedoStack(), undoRedoStack);
    }

    @Test
    public void add_addReversibleCommand_redoStackCleared() throws Exception {
        standardCommandList.forEach(undoRedoStack::add);

        // previous() populates redoStack
        undoRedoStack.previous();
        undoRedoStack.previous();

        undoRedoStack.add(reversibleAddCommand); // add() expected to clear redoStack
        UndoRedoStack expected = new UndoRedoStack();
        expected.add(reversibleAddCommand);
        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void add_addUndo_redoStackUncleared() throws Exception {
        standardCommandList.forEach(undoRedoStack::add);

        // previous() populates redoStack
        undoRedoStack.previous();
        undoRedoStack.previous();

        undoRedoStack.add(new UndoCommand()); // adding UndoCommand not expected to clear redoStack

        UndoRedoStack expected = new UndoRedoStack();
        expected.add(reversibleAddCommand);
        expected.add(reversibleClearCommand);
        expected.previous();
        expected.previous();


        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void add_addRedo_redoStackUncleared() throws Exception {
        standardCommandList.forEach(undoRedoStack::add);

        // previous() populates redoStack
        undoRedoStack.previous();
        undoRedoStack.previous();

        undoRedoStack.add(new RedoCommand()); // adding RedoCommand not expected to clear redoStack

        UndoRedoStack expected = new UndoRedoStack();
        expected.add(reversibleAddCommand);
        expected.add(reversibleClearCommand);
        expected.previous();
        expected.previous();

        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void previousAndNext_emptyUndoStack_throwsCommandException() {
        assertPreviousFailure();
        assertNextFailure();
    }

    @Test
    public void previousAndNext_onlyNonReversibleCommands_throwsCommandException() {
        undoRedoStack.add(nonReversibleListCommand);
        undoRedoStack.add(nonReversibleSelectCommand);

        assertPreviousFailure();
        assertNextFailure();
    }

    @Test
    public void previousAndNext_nonReversibleAndReversibleCommands() throws Exception {
        standardCommandList.forEach(undoRedoStack::add);

        assertEquals(reversibleClearCommand, undoRedoStack.previous());
        assertEquals(reversibleAddCommand, undoRedoStack.previous());
        assertPreviousFailure();

        assertEquals(reversibleAddCommand, undoRedoStack.next());
        assertEquals(reversibleClearCommand, undoRedoStack.next());
        assertNextFailure();
    }

    @Test
    public void equals() throws Exception {
        standardCommandList.forEach(undoRedoStack::add);

        // same values -> returns true
        UndoRedoStack copy = new UndoRedoStack();
        standardCommandList.forEach(copy::add);
        assertTrue(undoRedoStack.equals(copy));

        // same object -> returns true
        assertTrue(undoRedoStack.equals(undoRedoStack));

        // null -> returns false
        assertFalse(undoRedoStack.equals(null));

        // different types -> returns false
        assertFalse(undoRedoStack.equals(1));

        // different undoStack -> returns false
        UndoRedoStack differentUndoStack = new UndoRedoStack();
        standardCommandList.forEach(differentUndoStack::add);
        differentUndoStack.add(reversibleClearCommand);
        assertFalse(undoRedoStack.equals(differentUndoStack));

        // different redoStack -> returns false
        UndoRedoStack differentRedoStack = new UndoRedoStack();
        standardCommandList.forEach(differentRedoStack::add);
        differentRedoStack.add(reversibleClearCommand);
        undoRedoStack.previous(); // reversibleClearCommand in redoStack
        assertFalse(undoRedoStack.equals(differentRedoStack));
    }

    /**
     * Confirms that execution of {@code UndoRedoStack#previous()} fails and
     * {@code OutOfReversibleCommandException} is thrown and the error message equals
     * to {@value UndoCommand#MESSAGE_FAILURE}.
     */
    private void assertPreviousFailure() {
        try {
            undoRedoStack.previous();
            fail();
        } catch (OutOfReversibleCommandException ce) {
            assertEquals(ce.getMessage(), UndoCommand.MESSAGE_FAILURE);
        }
    }

    /**
     * Confirms that execution of {@code UndoRedoStack#next()} fails and
     * and {@code OutOfReversibleCommandException} is thrown and the error message
     * equals to {@value RedoCommand#MESSAGE_FAILURE}.
     */
    private void assertNextFailure() {
        try {
            undoRedoStack.next();
            fail();
        } catch (OutOfReversibleCommandException ce) {
            assertEquals(ce.getMessage(), RedoCommand.MESSAGE_FAILURE);
        }
    }
}

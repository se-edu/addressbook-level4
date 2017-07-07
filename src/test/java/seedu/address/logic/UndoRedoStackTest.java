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
import seedu.address.logic.commands.exceptions.OutOfElementsException;
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
    public void setUp() throws Exception {
        Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
        CommandHistory history = new CommandHistory();
        undoRedoStack = new UndoRedoStack();

        reversibleAddCommand.setData(model, history, undoRedoStack);
        reversibleClearCommand.setData(model, history, undoRedoStack);
        nonReversibleListCommand.setData(model, history, undoRedoStack);
        nonReversibleSelectCommand.setData(model, history, undoRedoStack);
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

        // popUndo() populates redoStack
        undoRedoStack.popUndo();
        undoRedoStack.popUndo();

        undoRedoStack.add(nonReversibleListCommand); // add() expected to clear redoStack
        assertEquals(new UndoRedoStack(), undoRedoStack);
    }

    @Test
    public void add_addReversibleCommand_redoStackCleared() throws Exception {
        standardCommandList.forEach(undoRedoStack::add);

        // popUndo() populates redoStack
        undoRedoStack.popUndo();
        undoRedoStack.popUndo();

        undoRedoStack.add(reversibleAddCommand); // add() expected to clear redoStack
        UndoRedoStack expected = new UndoRedoStack();
        expected.add(reversibleAddCommand);
        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void add_addUndoCommand_redoStackUncleared() throws Exception {
        standardCommandList.forEach(undoRedoStack::add);

        // popUndo() populates redoStack
        undoRedoStack.popUndo();
        undoRedoStack.popUndo();

        undoRedoStack.add(new UndoCommand()); // adding UndoCommand not expected to clear redoStack

        UndoRedoStack expected = new UndoRedoStack();
        expected.add(reversibleAddCommand);
        expected.add(reversibleClearCommand);
        expected.popUndo();
        expected.popUndo();

        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void add_addRedoCommand_redoStackUncleared() throws Exception {
        standardCommandList.forEach(undoRedoStack::add);

        // popUndo() populates redoStack
        undoRedoStack.popUndo();
        undoRedoStack.popUndo();

        undoRedoStack.add(new RedoCommand()); // adding RedoCommand not expected to clear redoStack

        UndoRedoStack expected = new UndoRedoStack();
        expected.add(reversibleAddCommand);
        expected.add(reversibleClearCommand);
        expected.popUndo();
        expected.popUndo();

        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void popUndoAndPopRedo_emptyUndoStack_throwsCommandException() {
        assertPreviousFailure();
        assertNextFailure();
    }

    @Test
    public void popUndoAndPopRedo_onlyNonReversibleCommands_throwsCommandException() {
        undoRedoStack.add(nonReversibleListCommand);
        undoRedoStack.add(nonReversibleSelectCommand);

        assertPreviousFailure();
        assertNextFailure();
    }

    @Test
    public void popUndoAndPopRedo_nonReversibleAndReversibleCommands() throws Exception {
        standardCommandList.forEach(undoRedoStack::add);

        assertEquals(reversibleClearCommand, undoRedoStack.popUndo());
        assertEquals(reversibleAddCommand, undoRedoStack.popUndo());
        assertPreviousFailure();

        assertEquals(reversibleAddCommand, undoRedoStack.popRedo());
        assertEquals(reversibleClearCommand, undoRedoStack.popRedo());
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
        undoRedoStack.popUndo(); // reversibleClearCommand in redoStack
        assertFalse(undoRedoStack.equals(differentRedoStack));
    }

    /**
     * Confirms that execution of {@code UndoRedoStack#popUndo()} fails and
     * {@code OutOfElementsException} is thrown and the error message equals
     * to {@value UndoCommand#MESSAGE_FAILURE}.
     */
    private void assertPreviousFailure() {
        try {
            undoRedoStack.popUndo();
            fail();
        } catch (OutOfElementsException ooee) {
            // expected exception thrown
        }
    }

    /**
     * Confirms that execution of {@code UndoRedoStack#popRedo()} fails and
     * and {@code OutOfElementsException} is thrown and the error message
     * equals to {@value RedoCommand#MESSAGE_FAILURE}.
     */
    private void assertNextFailure() {
        try {
            undoRedoStack.popRedo();
            fail();
        } catch (OutOfElementsException ooee) {
            // expected exception thrown
        }
    }
}

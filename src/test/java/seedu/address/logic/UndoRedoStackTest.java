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
    public void pushUndo_addNonReversibleCommand_redoStackCleared() throws Exception {
        fillUndoRedoStack(undoRedoStack, standardCommandList, 2);
        undoRedoStack.pushUndo(nonReversibleListCommand); // adding NonReversibleCommand expected to clear redoStack
        assertEquals(new UndoRedoStack(), undoRedoStack);
    }

    @Test
    public void pushUndo_addReversibleCommand_redoStackCleared() throws Exception {
        fillUndoRedoStack(undoRedoStack, standardCommandList, 2);
        undoRedoStack.pushUndo(reversibleAddCommand); // adding ReversibleCommand expected to clear redoStack

        UndoRedoStack expected = new UndoRedoStack();
        expected.pushUndo(reversibleAddCommand);
        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void pushUndo_addUndoCommand_redoStackUncleared() throws Exception {
        fillUndoRedoStack(undoRedoStack, standardCommandList, 2);
        undoRedoStack.pushUndo(new UndoCommand()); // adding UndoCommand not expected to clear redoStack

        UndoRedoStack expected = new UndoRedoStack();
        fillUndoRedoStack(expected, Arrays.asList(reversibleAddCommand, reversibleClearCommand), 2);

        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void pushUndo_addRedoCommand_redoStackUncleared() throws Exception {
        fillUndoRedoStack(undoRedoStack, standardCommandList, 2);
        undoRedoStack.pushUndo(new RedoCommand()); // adding RedoCommand not expected to clear redoStack

        UndoRedoStack expected = new UndoRedoStack();
        fillUndoRedoStack(expected, Arrays.asList(reversibleAddCommand, reversibleClearCommand), 2);

        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void popUndoAndPopRedo_emptyUndoStack_throwsCommandException() {
        assertPopUndoFailure();
        assertPopRedoFailure();
    }

    @Test
    public void popUndoAndPopRedo_onlyNonReversibleCommands_throwsCommandException() {
        undoRedoStack.pushUndo(nonReversibleListCommand);
        undoRedoStack.pushUndo(nonReversibleSelectCommand);

        assertPopUndoFailure();
        assertPopRedoFailure();
    }

    @Test
    public void popUndoAndPopRedo_nonReversibleAndReversibleCommands() throws Exception {
        standardCommandList.forEach(undoRedoStack::pushUndo);

        assertEquals(reversibleClearCommand, undoRedoStack.popUndo());
        assertEquals(reversibleAddCommand, undoRedoStack.popUndo());
        assertPopUndoFailure();

        assertEquals(reversibleAddCommand, undoRedoStack.popRedo());
        assertEquals(reversibleClearCommand, undoRedoStack.popRedo());
        assertPopRedoFailure();
    }

    @Test
    public void equals() throws Exception {
        standardCommandList.forEach(undoRedoStack::pushUndo);

        // same values -> returns true
        UndoRedoStack copy = new UndoRedoStack();
        standardCommandList.forEach(copy::pushUndo);
        assertTrue(undoRedoStack.equals(copy));

        // same object -> returns true
        assertTrue(undoRedoStack.equals(undoRedoStack));

        // null -> returns false
        assertFalse(undoRedoStack.equals(null));

        // different types -> returns false
        assertFalse(undoRedoStack.equals(1));

        // different undoStack -> returns false
        UndoRedoStack differentUndoStack = new UndoRedoStack();
        standardCommandList.forEach(differentUndoStack::pushUndo);
        differentUndoStack.pushUndo(reversibleClearCommand);
        assertFalse(undoRedoStack.equals(differentUndoStack));

        // different redoStack -> returns false
        UndoRedoStack differentRedoStack = new UndoRedoStack();
        standardCommandList.forEach(differentRedoStack::pushUndo);
        differentRedoStack.pushUndo(reversibleClearCommand);
        undoRedoStack.popUndo(); // reversibleClearCommand in redoStack
        assertFalse(undoRedoStack.equals(differentRedoStack));
    }

    /**
     * Helper method that adds {@code toAdd} into {@code undoRedoStack}, followed by calling
     * {@code undoRedoStack#popUndo()} for {@code numPopUndo} times.
     */
    private void fillUndoRedoStack(UndoRedoStack undoRedoStack, List<Command> toAdd, int numPopUndo) throws Exception {
        toAdd.forEach(undoRedoStack::pushUndo);
        for (int i = 0; i < numPopUndo; i++) {
            undoRedoStack.popUndo();
        }
    }

    /**
     * Confirms that execution of {@code UndoRedoStack#popUndo()} fails,
     * {@code OutOfElementsException} is thrown and the error message equals
     * to {@value UndoCommand#MESSAGE_FAILURE}.
     */
    private void assertPopUndoFailure() {
        try {
            undoRedoStack.popUndo();
            fail("The expected OutOfElementsException was not thrown.");
        } catch (OutOfElementsException ooee) {
            // expected exception thrown
        }
    }

    /**
     * Confirms that execution of {@code UndoRedoStack#popRedo()} fails,
     * {@code OutOfElementsException} is thrown and the error message
     * equals to {@value RedoCommand#MESSAGE_FAILURE}.
     */
    private void assertPopRedoFailure() {
        try {
            undoRedoStack.popRedo();
            fail("The expected OutOfElementsException was not thrown.");
        } catch (OutOfElementsException ooee) {
            // expected exception thrown
        }
    }
}

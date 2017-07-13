package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReversibleCommand;
import seedu.address.logic.commands.UndoCommand;

public class UndoRedoStackTest {
    // Commands
    private DummyCommand dummyCommandOne = new DummyCommand();
    private DummyReversibleCommand dummyReversibleCommandOne = new DummyReversibleCommand();
    private DummyReversibleCommand dummyReversibleCommandTwo = new DummyReversibleCommand();

    private UndoRedoStack undoRedoStack = new UndoRedoStack();

    @Test
    public void pushUndo_addNonReversibleCommand_redoStackCleared() {
        undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        undoRedoStack.pushUndo(dummyCommandOne); // adding NonReversibleCommand expected to clear redoStack
        assertEquals(new UndoRedoStack(), undoRedoStack);
    }

    @Test
    public void pushUndo_addReversibleCommand_redoStackCleared() {
        undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        undoRedoStack.pushUndo(dummyReversibleCommandOne); // adding ReversibleCommand expected to clear redoStack

        UndoRedoStack expected = prepareStack(Collections.singletonList(dummyReversibleCommandOne),
                Collections.emptyList());
        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void pushUndo_addNonReversibleCommand_emptyStack() {
        undoRedoStack.pushUndo(dummyCommandOne);
        assertEquals(new UndoRedoStack(), undoRedoStack);
    }

    @Test
    public void pushUndo_addUndoCommand_redoStackUncleared() {
        undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        undoRedoStack.pushUndo(new UndoCommand()); // adding UndoCommand not expected to clear redoStack

        UndoRedoStack expected = prepareStack(Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void pushUndo_addRedoCommand_redoStackUncleared() {
        undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        undoRedoStack.pushUndo(new RedoCommand()); // adding RedoCommand not expected to clear redoStack

        UndoRedoStack expected = prepareStack(Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        assertEquals(expected, undoRedoStack);
    }

    @Test
    public void popUndoAndPopRedo_emptyStack_throwsCommandException() {
        assertPopUndoFailure();
        assertPopRedoFailure();
    }

    @Test
    public void popUndoAndPopRedo_oneReversibleCommand() {
        undoRedoStack = prepareStack(Collections.singletonList(dummyReversibleCommandOne), Collections.emptyList());

        assertPopUndoSuccess(dummyReversibleCommandOne);
        assertPopUndoFailure();

        assertPopRedoSuccess(dummyReversibleCommandOne);
        assertPopRedoFailure();
    }

    @Test
    public void popUndoAndPopRedo_twoReversibleCommands() {
        undoRedoStack = prepareStack(Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo),
                Collections.emptyList());

        assertPopUndoSuccess(dummyReversibleCommandTwo);
        assertPopUndoSuccess(dummyReversibleCommandOne);
        assertPopRedoSuccess(dummyReversibleCommandOne);
        assertPopRedoSuccess(dummyReversibleCommandTwo);
    }

    @Test
    public void equals() {
        undoRedoStack = prepareStack(Arrays.asList(dummyReversibleCommandTwo, dummyReversibleCommandOne),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));

        // same values -> returns true
        UndoRedoStack copy = prepareStack(Arrays.asList(dummyReversibleCommandTwo, dummyReversibleCommandOne),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        assertTrue(undoRedoStack.equals(copy));

        // same object -> returns true
        assertTrue(undoRedoStack.equals(undoRedoStack));

        // null -> returns false
        assertFalse(undoRedoStack.equals(null));

        // different types -> returns false
        assertFalse(undoRedoStack.equals(1));

        // different undoStack -> returns false
        UndoRedoStack differentUndoStack = prepareStack(Collections.singletonList(dummyReversibleCommandTwo),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        assertFalse(undoRedoStack.equals(differentUndoStack));

        // different redoStack -> returns false
        UndoRedoStack differentRedoStack =
                prepareStack(Arrays.asList(dummyReversibleCommandTwo, dummyReversibleCommandOne),
                Collections.singletonList(dummyReversibleCommandTwo));
        assertFalse(undoRedoStack.equals(differentRedoStack));
    }

    /**
     * Helper method that adds {@code undoElements} into {@code UndoRedoStack#undoStack} and adds {@code redoElements}
     * into {@code UndoRedoStack#redoStack}. The first element in both {@code undoElements} and {@code redoElements}
     * will be the bottommost element in the respective stack in {@code undoRedoStack}, while the last element will
     * be the topmost element.
     */
    private UndoRedoStack prepareStack(List<ReversibleCommand> undoElements, List<ReversibleCommand> redoElements) {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        undoElements.forEach(undoRedoStack::pushUndo);

        Collections.reverse(redoElements);
        redoElements.forEach(undoRedoStack::pushUndo);
        redoElements.forEach(unused -> undoRedoStack.popUndo());

        return undoRedoStack;
    }

    /**
     * Confirms that {@code undoRedoStack#canUndo} returns true and
     * the result of {@code undoRedoStack#popUndo()} equals {@code expected}.
     */
    private void assertPopUndoSuccess(ReversibleCommand expected) {
        assertTrue(undoRedoStack.canUndo());
        assertEquals(expected, undoRedoStack.popUndo());
    }

    /**
     * Confirms that {@code undoRedoStack#canRedo} returns true and
     * the result of {@code undoRedoStack#popRedo()} equals {@code expected}.
     */
    private void assertPopRedoSuccess(ReversibleCommand expected) {
        assertTrue(undoRedoStack.canRedo());
        assertEquals(expected, undoRedoStack.popRedo());
    }

    /**
     * Confirms that {@code undoRedoStack#canUndo} returns false,
     * the execution of {@code undoRedoStack#popUndo()} fails and {@code EmptyStackException} is thrown.
     */
    private void assertPopUndoFailure() {
        assertFalse(undoRedoStack.canUndo());
        try {
            undoRedoStack.popUndo();
            fail("The expected EmptyStackException was not thrown.");
        } catch (EmptyStackException ese) {
            // expected exception thrown
        }
    }

    /**
     * Confirms that {@code undoRedoStack#canRedo} returns false,
     * the execution of {@code undoRedoStack#popRedo()} fails and {@code EmptyStackException} is thrown.
     */
    private void assertPopRedoFailure() {
        assertFalse(undoRedoStack.canRedo());
        try {
            undoRedoStack.popRedo();
            fail("The expected EmptyStackException was not thrown.");
        } catch (EmptyStackException ese) {
            // expected exception thrown
        }
    }

    class DummyCommand extends Command {
        @Override
        public CommandResult execute() {
            return new CommandResult("");
        }
    }

    class DummyReversibleCommand extends ReversibleCommand {
        @Override
        public CommandResult executeReversibleCommand() {
            return new CommandResult("");
        }
    }
}

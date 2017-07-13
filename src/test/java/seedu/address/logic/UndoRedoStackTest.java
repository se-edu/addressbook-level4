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
import seedu.address.logic.commands.ReversibleCommand;

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
        undoRedoStack.pushUndo(dummyCommandOne);
        assertEquals(new UndoRedoStack(), undoRedoStack);
    }

    @Test
    public void pushUndo_addReversibleCommand_redoStackCleared() {
        undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        undoRedoStack.pushUndo(dummyReversibleCommandOne);

        assertStackStatus(Collections.singletonList(dummyReversibleCommandOne), Collections.emptyList());
    }

    @Test
    public void pushUndo_addNonReversibleCommand_commandNotAdded() {
        undoRedoStack.pushUndo(dummyCommandOne);
        assertEquals(new UndoRedoStack(), undoRedoStack);
    }

    @Test
    public void canUndo() {
        // empty undo stack
        assertFalse(undoRedoStack.canUndo());

        // non-empty undo stack
        undoRedoStack = prepareStack(Collections.singletonList(dummyReversibleCommandOne), Collections.emptyList());
        assertTrue(undoRedoStack.canUndo());
    }

    @Test
    public void canRedo() {
        // empty redo stack
        assertFalse(undoRedoStack.canRedo());

        // non-empty redo stack
        undoRedoStack = prepareStack(Collections.emptyList(), Collections.singletonList(dummyReversibleCommandOne));
        assertTrue(undoRedoStack.canRedo());
    }

    @Test
    public void popUndo_emptyStack_throwsEmptyStackException() {
        assertPopUndoFailure();
        assertEquals(new UndoRedoStack(), undoRedoStack);
    }

    @Test
    public void popRedo_emptyStack_throwsEmptyStackException() {
        assertPopRedoFailure();
        assertEquals(new UndoRedoStack(), undoRedoStack);
    }

    @Test
    public void popUndo_twoReversibleCommands() {
        undoRedoStack = prepareStack(Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo),
                Collections.emptyList());

        assertPopUndoSuccess(dummyReversibleCommandTwo);
        assertStackStatus(Collections.singletonList(dummyReversibleCommandOne),
                Collections.singletonList(dummyReversibleCommandTwo));

        assertPopUndoSuccess(dummyReversibleCommandOne);
        assertStackStatus(Collections.emptyList(), Arrays.asList(dummyReversibleCommandTwo, dummyReversibleCommandOne));
    }

    @Test
    public void popRedo_twoReversibleCommands() {
        undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));

        assertPopRedoSuccess(dummyReversibleCommandTwo);
        assertStackStatus(Collections.singletonList(dummyReversibleCommandTwo),
                Collections.singletonList(dummyReversibleCommandOne));

        assertPopRedoSuccess(dummyReversibleCommandOne);
        assertStackStatus(Arrays.asList(dummyReversibleCommandTwo, dummyReversibleCommandOne), Collections.emptyList());
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
     * Asserts that the result of {@code undoRedoStack#popUndo()} equals {@code expected}.
     */
    private void assertPopUndoSuccess(ReversibleCommand expected) {
        assertEquals(expected, undoRedoStack.popUndo());
    }

    /**
     * Asserts that the result of {@code undoRedoStack#popRedo()} equals {@code expected}.
     */
    private void assertPopRedoSuccess(ReversibleCommand expected) {
        assertEquals(expected, undoRedoStack.popRedo());
    }

    /**
     * Asserts that the execution of {@code undoRedoStack#popUndo()} fails and {@code EmptyStackException} is thrown.
     */
    private void assertPopUndoFailure() {
        try {
            undoRedoStack.popUndo();
            fail("The expected EmptyStackException was not thrown.");
        } catch (EmptyStackException ese) {
            // expected exception thrown
        }
    }

    /**
     * Asserts that the execution of {@code undoRedoStack#popRedo()} fails and {@code EmptyStackException} is thrown.
     */
    private void assertPopRedoFailure() {
        try {
            undoRedoStack.popRedo();
            fail("The expected EmptyStackException was not thrown.");
        } catch (EmptyStackException ese) {
            // expected exception thrown
        }
    }

    /**
     * Asserts that {@code undoRedoStack#undoStack} equals {@code undoElements}, and {@code undoRedoStack#redoStack}
     * equals {@code redoElements}.
     */
    private void assertStackStatus(List<ReversibleCommand> undoElements, List<ReversibleCommand> redoElements) {
        assertEquals(prepareStack(undoElements, redoElements), undoRedoStack);
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

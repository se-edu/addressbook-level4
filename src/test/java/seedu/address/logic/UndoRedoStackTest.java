package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.UndoRedoStackUtil.prepareStack;

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
    private final DummyCommand dummyCommandOne = new DummyCommand();
    private final DummyReversibleCommand dummyReversibleCommandOne = new DummyReversibleCommand();
    private final DummyReversibleCommand dummyReversibleCommandTwo = new DummyReversibleCommand();

    private UndoRedoStack undoRedoStack = new UndoRedoStack();

    @Test
    public void push_nonReversibleCommand_redoStackClearedAndCommandNotAdded() {
        undoRedoStack = prepareStack(Collections.singletonList(dummyReversibleCommandOne),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        undoRedoStack.push(dummyCommandOne);

        assertStackStatus(Collections.singletonList(dummyReversibleCommandOne), Collections.emptyList());
    }

    @Test
    public void push_reversibleCommand_redoStackClearedAndCommandAdded() {
        undoRedoStack = prepareStack(Collections.singletonList(dummyReversibleCommandOne),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        undoRedoStack.push(dummyReversibleCommandOne);

        assertStackStatus(Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandOne),
                Collections.emptyList());
    }

    @Test
    public void push_emptyRedoStack_redoStackRemainsEmpty() {
        undoRedoStack = prepareStack(Collections.singletonList(dummyReversibleCommandOne), Collections.emptyList());
        undoRedoStack.push(dummyCommandOne);

        assertStackStatus(Collections.singletonList(dummyReversibleCommandOne), Collections.emptyList());
    }

    @Test
    public void push_undoCommand_redoStackNotClearedAndCommandNotAdded() {
        undoRedoStack = prepareStack(Collections.singletonList(dummyReversibleCommandOne),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        undoRedoStack.push(new UndoCommand());

        assertStackStatus(Collections.singletonList(dummyReversibleCommandOne),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
    }

    @Test
    public void push_redoCommand_redoStackNotClearedAndCommandNotAdded() {
        undoRedoStack = prepareStack(Collections.singletonList(dummyReversibleCommandOne),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
        undoRedoStack.push(new RedoCommand());

        assertStackStatus(Collections.singletonList(dummyReversibleCommandOne),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));
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
    public void popUndo_twoCommandsInUndoStack() {
        undoRedoStack = prepareStack(Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo),
                Collections.emptyList());

        assertPopUndoSuccess(dummyReversibleCommandTwo, Collections.singletonList(dummyReversibleCommandOne),
                Collections.singletonList(dummyReversibleCommandTwo));

        assertPopUndoSuccess(dummyReversibleCommandOne, Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandTwo, dummyReversibleCommandOne));

        assertPopUndoFailure(Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandTwo, dummyReversibleCommandOne));
    }

    @Test
    public void popRedo_twoCommandsInRedoStack() {
        undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(dummyReversibleCommandOne, dummyReversibleCommandTwo));

        assertPopRedoSuccess(dummyReversibleCommandTwo, Collections.singletonList(dummyReversibleCommandTwo),
                Collections.singletonList(dummyReversibleCommandOne));

        assertPopRedoSuccess(dummyReversibleCommandOne,
                Arrays.asList(dummyReversibleCommandTwo, dummyReversibleCommandOne), Collections.emptyList());

        assertPopRedoFailure(Arrays.asList(dummyReversibleCommandTwo, dummyReversibleCommandOne),
                Collections.emptyList());
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
     * Asserts that the result of {@code undoRedoStack#popUndo()} equals {@code expectedCommand}.
     * Also calls {@code assertStackStatus(List<ReversibleCommand>, List<ReversibleCommand>)}
     * with parameters {@code expectedUndoElements} and {@code expectedRedoElements} respectively.
     */
    private void assertPopUndoSuccess(ReversibleCommand expectedCommand, List<ReversibleCommand> expectedUndoElements,
                                      List<ReversibleCommand> expectedRedoElements) {
        assertEquals(expectedCommand, undoRedoStack.popUndo());
        assertStackStatus(expectedUndoElements, expectedRedoElements);
    }

    /**
     * Asserts that the result of {@code undoRedoStack#popRedo()} equals {@code expectedCommand}.
     * Also calls {@code assertStackStatus(List<ReversibleCommand>, List<ReversibleCommand>)}
     * with parameters {@code expectedUndoElements} and {@code expectedRedoElements} respectively.
     */
    private void assertPopRedoSuccess(ReversibleCommand expectedCommand, List<ReversibleCommand> expectedUndoElements,
                                      List<ReversibleCommand> expectedRedoElements) {
        assertEquals(expectedCommand, undoRedoStack.popRedo());
        assertStackStatus(expectedUndoElements, expectedRedoElements);
    }

    /**
     * Asserts that the execution of {@code undoRedoStack#popUndo()} fails and {@code EmptyStackException} is thrown.
     * Also calls {@code assertStackStatus(List<ReversibleCommand>, List<ReversibleCommand>)}
     * with parameters {@code expectedUndoElements} and {@code expectedRedoElements} respectively.
     */
    private void assertPopUndoFailure(List<ReversibleCommand> expectedUndoElements,
                                      List<ReversibleCommand> expectedRedoElements) {
        try {
            undoRedoStack.popUndo();
            fail("The expected EmptyStackException was not thrown.");
        } catch (EmptyStackException ese) {
            assertStackStatus(expectedUndoElements, expectedRedoElements);
        }
    }

    /**
     * Asserts that the execution of {@code undoRedoStack#popRedo()} fails and {@code EmptyStackException} is thrown.
     * Also calls {@code assertStackStatus(List<ReversibleCommand>, List<ReversibleCommand>)}
     * with parameters {@code expectedUndoElements} and {@code expectedRedoElements} respectively.
     */
    private void assertPopRedoFailure(List<ReversibleCommand> expectedUndoElements,
                                      List<ReversibleCommand> expectedRedoElements) {
        try {
            undoRedoStack.popRedo();
            fail("The expected EmptyStackException was not thrown.");
        } catch (EmptyStackException ese) {
            assertStackStatus(expectedUndoElements, expectedRedoElements);
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

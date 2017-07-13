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
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UndoableCommand;

public class UndoRedoStackTest {
    private final DummyCommand dummyCommandOne = new DummyCommand();
    private final DummyUndoableCommand dummyUndoableCommandOne = new DummyUndoableCommand();
    private final DummyUndoableCommand dummyUndoableCommandTwo = new DummyUndoableCommand();

    private UndoRedoStack undoRedoStack = new UndoRedoStack();

    @Test
    public void push_nonUndoableCommand_redoStackClearedAndCommandNotAdded() {
        // non-empty redoStack
        undoRedoStack = prepareStack(Collections.singletonList(dummyUndoableCommandOne),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));
        undoRedoStack.push(dummyCommandOne);
        assertStackStatus(Collections.singletonList(dummyUndoableCommandOne), Collections.emptyList());

        // empty redoStack
        undoRedoStack.push(dummyCommandOne);
        assertStackStatus(Collections.singletonList(dummyUndoableCommandOne), Collections.emptyList());
    }

    @Test
    public void push_undoableCommand_redoStackClearedAndCommandAdded() {
        // non-empty redoStack
        undoRedoStack = prepareStack(Collections.singletonList(dummyUndoableCommandOne),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));
        undoRedoStack.push(dummyUndoableCommandOne);
        assertStackStatus(Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandOne),
                Collections.emptyList());

        // empty redoStack
        undoRedoStack.push(dummyUndoableCommandOne);
        assertStackStatus(Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandOne, dummyUndoableCommandOne),
                Collections.emptyList());
    }

    @Test
    public void push_undoCommand_stackRemainsUnchanged() {
        // non-empty redoStack
        undoRedoStack = prepareStack(Collections.singletonList(dummyUndoableCommandOne),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));
        undoRedoStack.push(new UndoCommand());
        assertStackStatus(Collections.singletonList(dummyUndoableCommandOne),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));

        // empty redoStack
        undoRedoStack = prepareStack(Collections.singletonList(dummyUndoableCommandOne), Collections.emptyList());
        undoRedoStack.push(new UndoCommand());
        assertStackStatus(Collections.singletonList(dummyUndoableCommandOne), Collections.emptyList());
    }

    @Test
    public void push_redoCommand_stackRemainsUnchanged() {
        // non-empty redoStack
        undoRedoStack = prepareStack(Collections.singletonList(dummyUndoableCommandOne),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));
        undoRedoStack.push(new RedoCommand());
        assertStackStatus(Collections.singletonList(dummyUndoableCommandOne),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));

        // empty redoStack
        undoRedoStack = prepareStack(Collections.singletonList(dummyUndoableCommandOne), Collections.emptyList());
        undoRedoStack.push(new RedoCommand());
        assertStackStatus(Collections.singletonList(dummyUndoableCommandOne), Collections.emptyList());
    }

    @Test
    public void canUndo() {
        // empty undo stack
        assertFalse(undoRedoStack.canUndo());

        // non-empty undo stack
        undoRedoStack = prepareStack(Collections.singletonList(dummyUndoableCommandOne), Collections.emptyList());
        assertTrue(undoRedoStack.canUndo());
    }

    @Test
    public void canRedo() {
        // empty redo stack
        assertFalse(undoRedoStack.canRedo());

        // non-empty redo stack
        undoRedoStack = prepareStack(Collections.emptyList(), Collections.singletonList(dummyUndoableCommandOne));
        assertTrue(undoRedoStack.canRedo());
    }

    @Test
    public void popUndo() {
        undoRedoStack = prepareStack(Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo),
                Collections.emptyList());

        // multiple commands in undoStack
        assertPopUndoSuccess(dummyUndoableCommandTwo, Collections.singletonList(dummyUndoableCommandOne),
                Collections.singletonList(dummyUndoableCommandTwo));

        // single command in undoStack
        assertPopUndoSuccess(dummyUndoableCommandOne, Collections.emptyList(),
                Arrays.asList(dummyUndoableCommandTwo, dummyUndoableCommandOne));

        // no command in undoStack
        assertPopUndoFailure(Collections.emptyList(),
                Arrays.asList(dummyUndoableCommandTwo, dummyUndoableCommandOne));
    }

    @Test
    public void popRedo() {
        undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));

        // multiple commands in redoStack
        assertPopRedoSuccess(dummyUndoableCommandTwo, Collections.singletonList(dummyUndoableCommandTwo),
                Collections.singletonList(dummyUndoableCommandOne));

        // single command in redoStack
        assertPopRedoSuccess(dummyUndoableCommandOne,
                Arrays.asList(dummyUndoableCommandTwo, dummyUndoableCommandOne), Collections.emptyList());

        // no command in redoStack
        assertPopRedoFailure(Arrays.asList(dummyUndoableCommandTwo, dummyUndoableCommandOne),
                Collections.emptyList());
    }

    @Test
    public void equals() {
        undoRedoStack = prepareStack(Arrays.asList(dummyUndoableCommandTwo, dummyUndoableCommandOne),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));

        // same values -> returns true
        UndoRedoStack copy = prepareStack(Arrays.asList(dummyUndoableCommandTwo, dummyUndoableCommandOne),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));
        assertTrue(undoRedoStack.equals(copy));

        // same object -> returns true
        assertTrue(undoRedoStack.equals(undoRedoStack));

        // null -> returns false
        assertFalse(undoRedoStack.equals(null));

        // different types -> returns false
        assertFalse(undoRedoStack.equals(1));

        // different undoStack -> returns false
        UndoRedoStack differentUndoStack = prepareStack(Collections.singletonList(dummyUndoableCommandTwo),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));
        assertFalse(undoRedoStack.equals(differentUndoStack));

        // different redoStack -> returns false
        UndoRedoStack differentRedoStack =
                prepareStack(Arrays.asList(dummyUndoableCommandTwo, dummyUndoableCommandOne),
                Collections.singletonList(dummyUndoableCommandTwo));
        assertFalse(undoRedoStack.equals(differentRedoStack));
    }

    /**
     * Asserts that the result of {@code undoRedoStack#popUndo()} equals {@code expectedCommand}.
     * Also asserts that the content of the {@code undoRedoStack#undoStack} equals {@code undoElements},
     * and {@code undoRedoStack#redoStack} equals {@code redoElements}.
     */
    private void assertPopUndoSuccess(UndoableCommand expectedCommand, List<UndoableCommand> expectedUndoElements,
                                      List<UndoableCommand> expectedRedoElements) {
        assertEquals(expectedCommand, undoRedoStack.popUndo());
        assertStackStatus(expectedUndoElements, expectedRedoElements);
    }

    /**
     * Asserts that the result of {@code undoRedoStack#popRedo()} equals {@code expectedCommand}.
     * Also asserts that the content of the {@code undoRedoStack#undoStack} equals {@code undoElements},
     * and {@code undoRedoStack#redoStack} equals {@code redoElements}.
     */
    private void assertPopRedoSuccess(UndoableCommand expectedCommand, List<UndoableCommand> expectedUndoElements,
                                      List<UndoableCommand> expectedRedoElements) {
        assertEquals(expectedCommand, undoRedoStack.popRedo());
        assertStackStatus(expectedUndoElements, expectedRedoElements);
    }

    /**
     * Asserts that the execution of {@code undoRedoStack#popUndo()} fails and {@code EmptyStackException} is thrown.
     * Also asserts that the content of the {@code undoRedoStack#undoStack} equals {@code undoElements},
     * and {@code undoRedoStack#redoStack} equals {@code redoElements}.
     */
    private void assertPopUndoFailure(List<UndoableCommand> expectedUndoElements,
                                      List<UndoableCommand> expectedRedoElements) {
        try {
            undoRedoStack.popUndo();
            fail("The expected EmptyStackException was not thrown.");
        } catch (EmptyStackException ese) {
            assertStackStatus(expectedUndoElements, expectedRedoElements);
        }
    }

    /**
     * Asserts that the execution of {@code undoRedoStack#popRedo()} fails and {@code EmptyStackException} is thrown.
     * Also asserts that the content of the {@code undoRedoStack#undoStack} equals {@code undoElements},
     * and {@code undoRedoStack#redoStack} equals {@code redoElements}.
     */
    private void assertPopRedoFailure(List<UndoableCommand> expectedUndoElements,
                                      List<UndoableCommand> expectedRedoElements) {
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
    private void assertStackStatus(List<UndoableCommand> undoElements, List<UndoableCommand> redoElements) {
        assertEquals(prepareStack(undoElements, redoElements), undoRedoStack);
    }

    class DummyCommand extends Command {
        @Override
        public CommandResult execute() {
            return new CommandResult("");
        }
    }

    class DummyUndoableCommand extends UndoableCommand {
        @Override
        public CommandResult executeUndoableCommand() {
            return new CommandResult("");
        }
    }
}

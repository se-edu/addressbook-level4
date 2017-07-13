package seedu.address.logic;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ReversibleCommand;

/**
 * Stores the history of reversible commands executed.
 */
public class UndoRedoStack {
    private Stack<ReversibleCommand> undoStack;
    private Stack<ReversibleCommand> redoStack;

    public UndoRedoStack() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Appends {@code command} into the stack of commands that can be undone,
     * if it is of type {@code ReversibleCommand}. Upon calling this method,
     * previously undone commands (that are not redone) will be lost.
     */
    public void pushUndo(Command command) {
        redoStack.clear();

        if (!(command instanceof ReversibleCommand)) {
            return;
        }

        undoStack.add((ReversibleCommand) command);
    }

    /**
     * Pops and returns the next {@code ReversibleCommand} to be undone in the stack.
     */
    public ReversibleCommand popUndo() {
        ReversibleCommand toUndo = undoStack.pop();
        redoStack.push(toUndo);
        return toUndo;
    }

    /**
     * Pops and returns the next {@code ReversibleCommand} to be redone in the stack.
     */
    public ReversibleCommand popRedo() {
        ReversibleCommand toRedo = redoStack.pop();
        undoStack.push(toRedo);
        return toRedo;
    }

    /**
     * Returns true if calling {@code popUndo()} does not throw an {@code EmptyStackException}.
     */
    public boolean canUndo() {
        return !undoStack.empty();
    }

    /**
     * Returns true if calling {@code popRedo()} does not throw an {@code EmptyStackException}.
     */
    public boolean canRedo() {
        return !redoStack.empty();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UndoRedoStack)) {
            return false;
        }

        UndoRedoStack stack = (UndoRedoStack) other;

        // state check
        return undoStack.equals(stack.undoStack)
                && redoStack.equals(stack.redoStack);
    }
}

package seedu.address.logic;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ReversibleCommand;

/**
 * Maintains the undo-stack (the stack of commands that can be undone) and the redo-stack (the stack of
 * commands that can be undone).
 */
public class UndoRedoStack {
    private Stack<ReversibleCommand> undoStack;
    private Stack<ReversibleCommand> redoStack;

    public UndoRedoStack() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Pushes {@code command} onto the undo-stack if it is of type {@code ReversibleCommand}. Clears the redo-stack.
     */
    public void push(Command command) {
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
     * Returns true if there are more commands that can be undone.
     */
    public boolean canUndo() {
        return !undoStack.empty();
    }

    /**
     * Returns true if there are more commands that can be redone.
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

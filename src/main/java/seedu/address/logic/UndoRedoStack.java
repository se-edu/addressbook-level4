package seedu.address.logic;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReversibleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.OutOfElementsException;

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
     * Appends {@code command} into the list of commands that can be undone, if it is of type {@code ReversibleCommand}.
     * Upon calling this method, previously undone commands (that are not redone) will be lost.
     */
    public void pushUndo(Command command) {
        if (command instanceof UndoCommand || command instanceof RedoCommand) {
            return;
        }

        redoStack.clear();

        if (!(command instanceof ReversibleCommand)) {
            return;
        }

        undoStack.add((ReversibleCommand) command);
    }

    /**
     * Pops and returns the next {@code ReversibleCommand} to be undone in the list.
     * @throws OutOfElementsException if there are no more commands to be undone.
     */
    public ReversibleCommand popUndo() throws OutOfElementsException {
        if (undoStack.empty()) {
            throw new OutOfElementsException();
        }

        ReversibleCommand toUndo = undoStack.pop();
        redoStack.push(toUndo);
        return toUndo;
    }

    /**
     * Pops and returns the next {@code ReversibleCommand} to be redone in the list.
     * @throws OutOfElementsException if there are no more commands to be redone.
     */
    public ReversibleCommand popRedo() throws OutOfElementsException {
        if (redoStack.empty()) {
            throw new OutOfElementsException();
        }

        ReversibleCommand toRedo = redoStack.pop();
        undoStack.push(toRedo);
        return toRedo;
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

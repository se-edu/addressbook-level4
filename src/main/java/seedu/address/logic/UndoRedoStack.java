package seedu.address.logic;

import java.util.Stack;

import seedu.address.model.ReadOnlyAddressBook;

/**
 * Maintains the undo-stack (the stack of address book states that can be undone) and the redo-stack (the stack of
 * address book states that can be redone).
 */
public class UndoRedoStack {
    private Stack<ReadOnlyAddressBook> undoStack;
    private Stack<ReadOnlyAddressBook> redoStack;

    public UndoRedoStack() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Pushes the {@code previousAddressBookState} onto the undo-stack, and clears the redo-stack.
     */
    public void push(ReadOnlyAddressBook previousAddressBookState) {
        clearRedoStack();
        undoStack.push(previousAddressBookState);
    }

    /**
     * Pops and returns the next {@code toRestore} address book from the undo-stack, and pushes the {@code toSave}
     * address book into the redo-stack.
     */
    public ReadOnlyAddressBook popUndo(ReadOnlyAddressBook toSave) {
        ReadOnlyAddressBook toRestore = undoStack.pop();
        redoStack.push(toSave);
        return toRestore;
    }

    /**
     * Pops and returns the next {@code toRestore} address book from the redo-stack, and pushes the {@code toSave}
     * address book into the undo-stack.
     */
    public ReadOnlyAddressBook popRedo(ReadOnlyAddressBook toSave) {
        ReadOnlyAddressBook toRestore = redoStack.pop();
        undoStack.push(toSave);
        return toRestore;
    }

    /**
     * Clears the redo-stack.
     */
    public void clearRedoStack() {
        redoStack.clear();
    }

    /**
     * Returns true if there are more address book states to be restored.
     */
    public boolean canUndo() {
        return !undoStack.empty();
    }

    /**
     * Returns true if there are more address book states to be restored.
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

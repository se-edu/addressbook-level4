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
     * Pushes the {@code currentAddressBookState} onto the undo-stack, and clears the redo-stack.
     */
    public void push(ReadOnlyAddressBook currentAddressBookState) {
        clearRedoStack();
        undoStack.push(currentAddressBookState);
    }

    /**
     * Pops the top address book from the undo-stack and pushes the it into the redo-stack, and returns
     * the new top address book of the undo-stack to restore.
     */
    public ReadOnlyAddressBook popUndo() {
        ReadOnlyAddressBook toPop = undoStack.pop();
        redoStack.push(toPop);
        return undoStack.peek();
    }

    /**
     * Pops and returns the address book to restore from the redo-stack, and pushes the same address book into the
     * undo-stack.
     */
    public ReadOnlyAddressBook popRedo() {
        ReadOnlyAddressBook toPop = redoStack.pop();
        undoStack.push(toPop);
        return toPop;
    }

    /**
     * Clears the redo-stack.
     */
    public void clearRedoStack() {
        redoStack.clear();
    }

    /**
     * Returns true if there are more previous address book states to be restored.
     */
    public boolean canUndo() {
        return undoStack.size() > 1;
    }

    /**
     * Returns true if there are more previously undone address book states to be restored.
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

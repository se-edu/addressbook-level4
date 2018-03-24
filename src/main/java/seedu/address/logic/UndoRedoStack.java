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
     * Pushes the {@code currentAddressBookState} onto the undo-stack.
     */
    public void push(ReadOnlyAddressBook currentAddressBookState) {
        undoStack.push(currentAddressBookState);
    }

    /**
     * Pops the address book from the undo-stack and pushes it into the redo-stack, and returns
     * the new address book on top of the undo-stack.
     */
    public ReadOnlyAddressBook popUndo() {
        ReadOnlyAddressBook toPop = undoStack.pop();
        redoStack.push(toPop);
        return undoStack.peek();
    }

    /**
     * Pops the address book from the redo-stack and pushes it into the undo-stack, and returns
     * the same address book popped.
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
     * Returns true if {@code popUndo()} has an address book state to return.
     */
    public boolean canUndo() {
        return undoStack.size() > 1;
    }

    /**
     * Returns true if {@code popRedo()} has an address book state to return.
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

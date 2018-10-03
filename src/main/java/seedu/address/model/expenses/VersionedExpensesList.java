package seedu.address.model.expenses;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedExpensesList extends ExpensesList {
    private final List<ReadOnlyExpensesList> expensesStateList;
    private int currentStatePointer;

    public VersionedExpensesList(ReadOnlyExpensesList initialState) {
        super(initialState);

        expensesStateList = new ArrayList<>();
        expensesStateList.add(new ExpensesList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        expensesStateList.add(new ExpensesList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        expensesStateList.subList(currentStatePointer + 1, expensesStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(expensesStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(expensesStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < expensesStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedExpensesList)) {
            return false;
        }

        VersionedExpensesList otherVersionedExpensesList = (VersionedExpensesList) other;

        // state check
        return super.equals(otherVersionedExpensesList)
                && expensesStateList.equals(otherVersionedExpensesList.expensesStateList)
                && currentStatePointer == otherVersionedExpensesList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}

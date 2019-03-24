package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ExpenditureList} that keeps track of its own history.
 */
public class VersionedExpenditureList extends ExpenditureList {
    private final List<ReadOnlyExpenditureList> expenditureListStateList;
    private int currentStatePointer;

    public VersionedExpenditureList(ReadOnlyExpenditureList initialState) {
        super(initialState);

        expenditureListStateList = new ArrayList<>();
        expenditureListStateList.add(new ExpenditureList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ExpenditureList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        expenditureListStateList.add(new ExpenditureList(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        expenditureListStateList.subList(currentStatePointer + 1, expenditureListStateList.size()).clear();
    }

    /**
     * Restores the expenditure list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(expenditureListStateList.get(currentStatePointer));
    }

    /**
     * Restores the expenditure list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(expenditureListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has expenditure list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has expenditure list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < expenditureListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedExpenditureList)) {
            return false;
        }

        VersionedExpenditureList otherVersionedExpenditureList = (VersionedExpenditureList) other;

        // state check
        return super.equals(otherVersionedExpenditureList)
                && expenditureListStateList.equals(otherVersionedExpenditureList.expenditureListStateList)
                && currentStatePointer == otherVersionedExpenditureList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of expenditureListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of expenditureListState list, unable to redo.");
        }
    }
}

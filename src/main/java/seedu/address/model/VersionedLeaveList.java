package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

public class VersionedLeaveList extends LeaveList {


    private final List<ReadOnlyLeaveList> leaveListStateList;
    private int currentStatePointer;

    public VersionedLeaveList(ReadOnlyLeaveList initialState) {
        super(initialState);

        leaveListStateList = new ArrayList<>();
        leaveListStateList.add(new LeaveList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        leaveListStateList.add(new LeaveList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        leaveListStateList.subList(currentStatePointer + 1, leaveListStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(leaveListStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(leaveListStateList.get(currentStatePointer));
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
        return currentStatePointer < leaveListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedLeaveList)) {
            return false;
        }

        VersionedLeaveList otherVersionedLeaveList = (VersionedLeaveList) other;

        // state check
        return super.equals(otherVersionedLeaveList)
                && leaveListStateList.equals(otherVersionedLeaveList.leaveListStateList)
                && currentStatePointer == otherVersionedLeaveList.currentStatePointer;
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


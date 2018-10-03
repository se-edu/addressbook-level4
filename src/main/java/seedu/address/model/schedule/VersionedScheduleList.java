package seedu.address.model.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedScheduleList extends ScheduleList {

    private final List<ReadOnlyScheduleList> scheduleListStateList;
    private int currentStatePointer;

    public VersionedScheduleList(ReadOnlyScheduleList initialState) {
        super(initialState);

        scheduleListStateList = new ArrayList<>();
        scheduleListStateList.add(new ScheduleList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        scheduleListStateList.add(new ScheduleList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        scheduleListStateList.subList(currentStatePointer + 1, scheduleListStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(scheduleListStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(scheduleListStateList.get(currentStatePointer));
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
        return currentStatePointer < scheduleListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedScheduleList)) {
            return false;
        }

        VersionedScheduleList otherVersionedScheduleList = (VersionedScheduleList) other;

        // state check
        return super.equals(otherVersionedScheduleList)
                && scheduleListStateList.equals(otherVersionedScheduleList.scheduleListStateList)
                && currentStatePointer == otherVersionedScheduleList.currentStatePointer;
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

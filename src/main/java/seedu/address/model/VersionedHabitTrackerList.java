package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code HabitTrackerList} that keeps track of its own history.
 */
public class VersionedHabitTrackerList extends HabitTrackerList {
    private final List<ReadOnlyHabitTrackerList> habitTrackerListStateList;
    private int currentStatePointer;

    public VersionedHabitTrackerList(ReadOnlyHabitTrackerList initialState) {
        super(initialState);

        habitTrackerListStateList = new ArrayList<>();
        habitTrackerListStateList.add(new HabitTrackerList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code HabitTrackerList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        habitTrackerListStateList.add(new HabitTrackerList(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        habitTrackerListStateList.subList(currentStatePointer + 1, habitTrackerListStateList.size()).clear();
    }

    /**
     * Restores the habit tracker list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(habitTrackerListStateList.get(currentStatePointer));
    }

    /**
     * Restores the habit tracker list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(habitTrackerListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has habit tracker list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has habit tracker list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < habitTrackerListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedHabitTrackerList)) {
            return false;
        }

        VersionedHabitTrackerList otherVersionedHabitTrackerList = (VersionedHabitTrackerList) other;

        // state check
        return super.equals(otherVersionedHabitTrackerList)
                && habitTrackerListStateList.equals(otherVersionedHabitTrackerList.habitTrackerListStateList)
                && currentStatePointer == otherVersionedHabitTrackerList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of habitTrackerListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of habitTrackerListState list, unable to redo.");
        }
    }
}


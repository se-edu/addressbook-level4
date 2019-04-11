package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ContactList} that keeps track of its own history.
 */
public class VersionedContactList extends ContactList {

    private final List<ReadOnlyContactList> contactListStateList;
    private int currentStatePointer;

    public VersionedContactList(ReadOnlyContactList initialState) {
        super(initialState);

        contactListStateList = new ArrayList<>();
        contactListStateList.add(new ContactList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ContactList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        contactListStateList.add(new ContactList(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        contactListStateList.subList(currentStatePointer + 1, contactListStateList.size()).clear();
    }

    /**
     * Restores the contact list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(contactListStateList.get(currentStatePointer));
    }

    /**
     * Restores the contact list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(contactListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has contact list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has contact list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < contactListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedContactList)) {
            return false;
        }

        VersionedContactList otherVersionedContactList = (VersionedContactList) other;

        // state check
        return super.equals(otherVersionedContactList)
                && contactListStateList.equals(otherVersionedContactList.contactListStateList)
                && currentStatePointer == otherVersionedContactList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of contactListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of contactListState list, unable to redo.");
        }
    }
}

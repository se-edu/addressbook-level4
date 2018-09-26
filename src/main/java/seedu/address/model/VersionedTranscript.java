package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Transcript} that keeps track of its own history.
 */
public class VersionedTranscript extends Transcript {

    private final List<ReadOnlyTranscript> transcriptStateList;
    private int currentStatePointer;

    public VersionedTranscript(ReadOnlyTranscript initialState) {
        super(initialState);

        transcriptStateList = new ArrayList<>();
        transcriptStateList.add(new Transcript(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code Transcript} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        transcriptStateList.add(new Transcript(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        transcriptStateList.subList(currentStatePointer + 1, transcriptStateList.size()).clear();
    }

    /**
     * Restores the transcript to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(transcriptStateList.get(currentStatePointer));
    }

    /**
     * Restores the transcript to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(transcriptStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has transcript states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has transcript states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < transcriptStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTranscript)) {
            return false;
        }

        VersionedTranscript otherVersionedTranscript = (VersionedTranscript) other;

        // state check
        return super.equals(otherVersionedTranscript)
                && transcriptStateList.equals(otherVersionedTranscript.transcriptStateList)
                && currentStatePointer == otherVersionedTranscript.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of transcriptState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of transcriptState list, unable to redo.");
        }
    }
}

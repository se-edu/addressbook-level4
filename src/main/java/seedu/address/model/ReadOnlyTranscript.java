package seedu.address.model;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a Transcript.
 */
public interface ReadOnlyTranscript {
    /**
     * Returns an unmodifiable view of the module list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Integer> getModuleList();

}

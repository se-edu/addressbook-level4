package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.module.Module;

/**
 * Unmodifiable view of an transcript
 */
public interface ReadOnlyTranscript {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();
}

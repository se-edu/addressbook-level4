package seedu.address.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javafx.collections.ObservableList;

import seedu.address.model.capgoal.CapGoal;
import seedu.address.model.module.Module;
import seedu.address.storage.JsonTranscriptDeserializer;

//@@author jeremiah-ang
/**
 * Unmodifiable view of a Transcript.
 */
@JsonDeserialize(using = JsonTranscriptDeserializer.class)
public interface ReadOnlyTranscript {
    /**
     * Returns an unmodifiable view of the module list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Returns the CapGoal of this transcript
     */
    CapGoal getCapGoal();

    /**
     * Returns the current CAP of this transcript
     */
    double getCurrentCap();
}

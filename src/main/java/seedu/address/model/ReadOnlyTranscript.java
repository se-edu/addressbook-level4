package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.module.Module;
import seedu.address.storage.JsonTranscriptDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Unmodifiable view of a Transcript.
 */
@JsonDeserialize(using = JsonTranscriptDeserializer.class)
public interface ReadOnlyTranscript  {

    /**
     * Returns an unmodifiable view of the module list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();
}

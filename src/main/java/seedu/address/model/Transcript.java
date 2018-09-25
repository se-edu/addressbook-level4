package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * A Transcript.
 */
public class Transcript implements ReadOnlyTranscript {
    @Override
    public ObservableList<Integer> getModuleList() {
        ObservableList<Integer> modules = FXCollections.observableArrayList(1, 2, 3);
        return FXCollections.unmodifiableObservableList(modules);
    }
}

package seedu.address.model.datatypes;

import javafx.beans.Observable;
import javafx.util.Callback;

import java.util.List;

/**
 * Utility methods for extracting all publicly relevant {@code Observable} fields for easy full object binding and
 * invalidation listening.
 *
 * @see javafx.collections.FXCollections#observableArrayList(Callback)
 * @see javafx.collections.FXCollections#observableList(List, Callback)
 */
public interface ExtractableObservables {

    /**
     * Useful as extractor argument to {@link javafx.collections.FXCollections#observableArrayList(Callback)} or
     * {@link javafx.collections.FXCollections#observableList(List, Callback)}.
     * Same as calling {@code source.extractObservables()}.
     *
     * @see #extractObservables()
     * @param source
     * @return all publicly relevant {@code Observable} fields in {@code source}.
     */
    static Observable[] extractFrom(ExtractableObservables source) {
        return source.extractObservables();
    }

    /**
     * @see #extractFrom(ExtractableObservables)
     * @return all publicly relevant {@code Observable} fields in this object.
     */
    Observable[] extractObservables();
}

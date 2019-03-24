package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.workout.Workout;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyWorkoutBook extends Observable {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Workout> getWorkoutList();

}
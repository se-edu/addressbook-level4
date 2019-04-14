
package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.workout.Workout;

/**
 * Unmodifiable view of an workout book
 */
public interface ReadOnlyWorkoutBook extends Observable {

    /**
     * Returns an unmodifiable view of the workout list.
     */
    ObservableList<Workout> getWorkoutList();
}

package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.workout.Workout;
import seedu.address.model.workout.WorkoutList;

/**
 * Wraps all data at the workout-book level
 */
public class WorkoutBook implements ReadOnlyWorkoutBook {

    private final WorkoutList workouts;

    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    {
        workouts = new WorkoutList();

    }

    public WorkoutBook() {}

    /**
     * Creates an WorkoutList using the Workouts in the {@code toBeCopied}
     */
    public WorkoutBook(ReadOnlyWorkoutBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the workout list with {@code workouts}.
     */

    public void setWorkouts(List<Workout> workouts) {
        this.workouts.setWorkout(workouts);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code WorkoutList} with {@code newData}.
     */
    public void resetData(ReadOnlyWorkoutBook newData) {
        requireNonNull(newData);
        setWorkouts(newData.getWorkoutList());
    }

    /**
     * Adds a workout to the workout book.
     */
    public void addWorkout(Workout w) {
        workouts.add(w);
        indicateModified();
    }
    public ArrayList<Workout> getRecent() {
        return workouts.getRecent();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the workout book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return workouts.asUnmodifiableObservableList().size() + " workouts";
        // TODO: refine later
    }

    @Override
    public ObservableList<Workout> getWorkoutList() {
        return workouts.asUnmodifiableObservableList();
    }

    @Override
    public int hashCode() {
        return workouts.hashCode();
    }




}

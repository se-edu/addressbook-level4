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
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class WorkoutBook implements ReadOnlyWorkoutBook {

    private final WorkoutList workouts;

    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();


    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        workouts = new WorkoutList();

    }

    public WorkoutBook() {}

    /**
     * Creates an ContactList using the Persons in the {@code toBeCopied}
     */
    public WorkoutBook(ReadOnlyWorkoutBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setWorkouts(List<Workout> workouts) {
        this.workouts.setWorkout(workouts);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code ContactList} with {@code newData}.
     */
    public void resetData(ReadOnlyWorkoutBook newData) {
        requireNonNull(newData);
        setWorkouts(newData.getWorkoutList());
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
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
     * Notifies listeners that the address book has been modified.
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

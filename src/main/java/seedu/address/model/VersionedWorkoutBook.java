package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * some workoutbook
 */
public class VersionedWorkoutBook extends WorkoutBook {
    private final List<ReadOnlyWorkoutBook> workoutBookStateList;
    private int currentStatePointer;

    public VersionedWorkoutBook(ReadOnlyWorkoutBook initialState) {
        super(initialState);
        workoutBookStateList = new ArrayList<>();
        workoutBookStateList.add(new WorkoutBook(initialState));
        currentStatePointer = 0;
    }
    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        workoutBookStateList.add(new WorkoutBook(this));
        currentStatePointer++;
        indicateModified();
    }

    /**
     * resets the Data
      * @param newData
     */
   public void resetData(ReadOnlyWorkoutBook newData) {
        requireNonNull(newData);
        setWorkouts(newData.getWorkoutList());
    }

    private void removeStatesAfterCurrentPointer() {
        workoutBookStateList.subList(currentStatePointer + 1, workoutBookStateList.size()).clear();
    }

}

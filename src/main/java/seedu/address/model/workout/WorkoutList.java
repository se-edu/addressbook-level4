package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * WorkoutList class
 */
public class WorkoutList implements Iterable<Workout> {

    private final ObservableList<Workout> internalList = FXCollections.observableArrayList();
    private final ObservableList<Workout> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Workout toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setWorkout(List<Workout> workouts) {
        requireAllNonNull(workouts);
        internalList.setAll(workouts);
    }
    public ArrayList<Workout> getRecent() {
        ArrayList<Workout> results = new ArrayList<Workout>();
        int i;
        for (i = 0; i < internalList.size(); i++) {
            results.add(internalList.get(i));
        }

        while (results.size() > 5) {
            results.remove(0);
        }
        return results;
    }





    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Workout> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Workout> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}

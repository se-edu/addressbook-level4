package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

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
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    /*public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }*/



    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */

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

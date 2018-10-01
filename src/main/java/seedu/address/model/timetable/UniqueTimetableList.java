package seedu.address.model.timetable;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 */
public class UniqueTimetableList implements Iterable<Timetable> {

    private final ObservableList<Timetable> internalList = FXCollections.observableArrayList();

    /**
     * Adds a timetable to the list.
     * The person must not already exist in the list.
     */
    public void add(Timetable toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }
    @Override
    public Iterator<Timetable> iterator() {
        return internalList.iterator();
    }

    public int numberOfTimetable() {
        return internalList.size();
    }

}

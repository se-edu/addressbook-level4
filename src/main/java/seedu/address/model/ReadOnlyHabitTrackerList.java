package seedu.address.model;


import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.model.habit.Habit;
import java.util.Observable;

/**
 * Unmodifiable view of the habit tracker list.
 */
public abstract class ReadOnlyHabitTrackerList extends Observable {

    public abstract void addListener(InvalidationListener listener);

    public abstract void removeListener(InvalidationListener listener);

    /**
     * Returns an unmodifiable view of the habit tracker list.
     */
    public abstract ObservableList<Habit> getHabitList();
}

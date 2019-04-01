package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.habit.Habit;
import seedu.address.model.habit.HabitList;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.purchase.PurchaseList;

/**
 * Wraps all data at the habit tracker list level
 */


public class HabitTrackerList extends ReadOnlyHabitTrackerList {

    private final HabitList habits;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();


    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        habits = new HabitList();

    }

    public HabitTrackerList() {}

    /**
     * Creates a HabitTrackerList using the Habits in the {@code toBeCopied}
     */
    public HabitTrackerList(ReadOnlyHabitTrackerList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the habit list with {@code habits}.
     */
    public void setHabits(List<Habit> habits) {
        this.habits.setHabits(habits);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code ExpenditureList} with {@code newData}.
     */
    public void resetData(ReadOnlyHabitTrackerList newData) {
        requireNonNull(newData);

        setHabits(newData.getHabitList());
    }

    //// habit-level operations

    /**
     * Adds a habit to the habit tracker.
     * The person must not already exist in the address book.
     */
    public void addHabit(Habit h) {
        habits.add(h);
        indicateModified();
    }



    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
  /*      public void setPerson(Person target, Person editedPerson) {
            requireNonNull(editedPerson);

            persons.setPerson(target, editedPerson);
            indicateModified();
        }
*/
    /**
     * Removes {@code key} from this {@code ExpenditureList}.
     * {@code key} must exist in the expenditure list.
     */
    public void removeHabit(Habit key) {
        habits.remove(key);
        indicateModified();
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
     * Notifies listeners that the expenditure list has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners((Observable) this);
    }

    //// util methods

    @Override
    public String toString() {
        return habits.asUnmodifiableObservableList().size() + " habits";
        // TODO: refine later
    }

    @Override
    public ObservableList<Habit> getHabitList() {
        return habits.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HabitTrackerList // instanceof handles nulls
                && habits.equals(((HabitTrackerList) other).habits));
    }

    @Override
    public int hashCode() {
        return habits.hashCode();
    }

}

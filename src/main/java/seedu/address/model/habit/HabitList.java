package seedu.address.model.habit;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.purchase.exceptions.PurchaseNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of habits that does not allow nulls.
 */

public class HabitList implements Iterable<Habit> {
    private final ObservableList<Habit> internalList = FXCollections.observableArrayList();
    private final ObservableList<Habit> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent habit as the given argument.
     */
    public boolean contains(Habit toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameHabit);
    }

    /**
     * Adds a habit to the list.
     * The habit must not already exist in the list.
     */
    public void add(Habit toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the habit {@code target} in the list with {@code editedHabit}.
     * {@code target} must exist in the list.
     */
    public void setHabit(Habit target, Habit editedHabit) {
        requireAllNonNull(target, editedHabit);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PurchaseNotFoundException();
        }

        internalList.set(index, editedHabit);
    }

    /**
     * Removes the equivalent habit from the list.
     * The habit must exist in the list.
     */
    public void remove(Habit toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PurchaseNotFoundException();
        }
    }

    public void setHabits(HabitList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code habits}.
     * {@code habits} must not contain duplicate habits.
     */
    public void setHabits(List<Habit> habits) {
        requireAllNonNull(habits);
        internalList.setAll(habits);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Habit> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Habit> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HabitList // instanceof handles nulls
                && internalList.equals(((HabitList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}


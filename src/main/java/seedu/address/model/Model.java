package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ReadOnlyExpensesList;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.model.schedule.Schedule;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Schedule> PREDICATE_SHOW_ALL_SCHEDULES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetAddressBookData(ReadOnlyAddressBook newData);
    void resetDataExpenses(ReadOnlyExpensesList newData);
    void resetScheduleListData(ReadOnlyScheduleList newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();
    ReadOnlyExpensesList getExpensesList();
    ReadOnlyScheduleList getScheduleList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasExpenses(Expenses expenses);
    boolean hasPerson(Person person);
    boolean hasSchedule(Schedule schedule);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteExpenses(Expenses target);
    void deletePerson(Person target);
    void deleteSchedule(Schedule target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addExpenses (Expenses expenses);
    void addPerson(Person person);
    void addSchedule(Schedule schedule);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updateExpenses(Expenses target, Expenses editedExpenses);
    void updatePerson(Person target, Person editedPerson);
    void updateSchedule(Schedule target, Schedule editedSchedule);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();
    ObservableList<Schedule> getFilteredScheduleList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
    void updateFilteredScheduleList(Predicate<Schedule> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();
    boolean canUndoScheduleList();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();
    boolean canRedoScheduleList();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();
    void undoScheduleList();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();
    void redoScheduleList();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();
    void commitExpensesList();
    void commitScheduleList();

}

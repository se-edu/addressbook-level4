package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ExpensesListChangedEvent;
import seedu.address.commons.events.model.ScheduleListChangedEvent;
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
import seedu.address.model.addressbook.VersionedAddressBook;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.expenses.ReadOnlyExpensesList;
import seedu.address.model.expenses.VersionedExpensesList;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.model.schedule.VersionedScheduleList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final VersionedExpensesList versionedExpensesList;
    private final VersionedScheduleList versionedScheduleList;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Expenses> filteredExpenses;
    private final FilteredList<Schedule> filteredSchedule;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyExpensesList expensesList,
                        ReadOnlyScheduleList scheduleList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedExpensesList = new VersionedExpensesList(expensesList);
        versionedScheduleList = new VersionedScheduleList(scheduleList);
        filteredExpenses = new FilteredList<>(versionedExpensesList.getExpensesRequestList());
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredSchedule = new FilteredList<>(versionedScheduleList.getScheduleList());
    }

    public ModelManager() {
        this(new AddressBook(), new ExpensesList(), new ScheduleList(), new UserPrefs());
    }

    //-----------------------------------------------------------------------------
    @Override
    public void resetAddressBookData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public void resetDataExpenses(ReadOnlyExpensesList newData) {
        versionedExpensesList.resetData(newData);
        indicateExpensesListChanged();
    }

    @Override
    public void resetScheduleListData(ReadOnlyScheduleList newData) {
        versionedScheduleList.resetData(newData);
        indicateAddressBookChanged();
    }

    //-----------------------------------------------------------------------------
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyExpensesList getExpensesList() {
        return versionedExpensesList; }

    @Override
    public ReadOnlyScheduleList getScheduleList() {

        return versionedScheduleList;
    }

    //-----------------------------------------------------------------------------

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    private void indicateExpensesListChanged() {
        raise(new ExpensesListChangedEvent(versionedExpensesList)); }

    private void indicateScheduleListChanged() {
        raise(new ScheduleListChangedEvent(versionedScheduleList));
    }


    //-----------------------------------------------------------------------------
    @Override
    public boolean hasExpenses(Expenses expenses) {
        requireNonNull(expenses);
        return versionedExpensesList.hasExpenses(expenses);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasSchedule(Schedule target) {
        requireNonNull(target);
        return versionedScheduleList.hasSchedule(target);
    }

    //-----------------------------------------------------------------------------
    @Override
    public void deleteExpenses(Expenses target) {
        versionedExpensesList.removeExpenses(target);
        indicateExpensesListChanged();
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteSchedule(Schedule target) {
        versionedScheduleList.removePerson(target);
        indicateScheduleListChanged();
    }

    //-----------------------------------------------------------------------------
    @Override
    public void addExpenses(Expenses expenses) {
        versionedExpensesList.addExpenses(expenses);
        indicateExpensesListChanged();
    }


    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addSchedule(Schedule schedule) {
        versionedScheduleList.addSchedule(schedule);
        updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
        indicateScheduleListChanged();
    }

    //-----------------------------------------------------------------------------
    @Override
    public void updateExpenses(Expenses target, Expenses editedExpenses) {
        requireAllNonNull(target, editedExpenses);
        versionedExpensesList.updateExpenses(target, editedExpenses);
        indicateExpensesListChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void updateSchedule(Schedule target, Schedule editedSchedule) {
        requireAllNonNull(target, editedSchedule);

        versionedScheduleList.updateSchedule(target, editedSchedule);
        indicateScheduleListChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public ObservableList<Schedule> getFilteredScheduleList() {
        return FXCollections.unmodifiableObservableList(filteredSchedule);
    }

    //=========== Filtered Person List Accessors =============================================================
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredScheduleList(Predicate<Schedule> predicate) {
        requireNonNull(predicate);
        filteredSchedule.setPredicate(predicate);
    }

    //=========== Undo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canUndoScheduleList() {
        return versionedScheduleList.canUndo();
    }

    //=========== Redo =================================================================================
    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public boolean canRedoScheduleList() {
        return versionedScheduleList.canRedo();
    }

    //-----------------------------------------------------------------------------

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void undoScheduleList() {
        versionedScheduleList.undo();
        indicateScheduleListChanged();
    }

    //-----------------------------------------------------------------------------
    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoScheduleList() {
        versionedScheduleList.redo();
        indicateScheduleListChanged();
    }

    //-----------------------------------------------------------------------------
    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    public void commitExpensesList() {
        versionedExpensesList.commit();
    }

    public void commitScheduleList() {
        versionedScheduleList.commit();
    }

    //-----------------------------------------------------------------------------

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}

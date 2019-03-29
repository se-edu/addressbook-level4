package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.workout.Workout;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    ReadOnlyTaskList getTaskList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);


    void addTask(Task task);


    void commitTaskList();
    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

     void updateFilteredTaskList(Predicate<Task> predicate);
    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Returns the selected person in the filtered person list.
     * null if no person is selected.
     */
    Person getSelectedPerson();

    /**
     * Sets the selected person in the filtered person list.
     */
    void setSelectedPerson(Person person);

    public ObservableList<Task> getFilteredTaskList();

    public ReadOnlyProperty<Task> selectedTaskProperty();

    void setSelectedTask(Task task);

    void setTask(Task target, Task editedTask);





    /** {@code Predicate} that always evaluate to true */
    Predicate<Purchase> PREDICATE_SHOW_ALL_PURCHASES = unused -> true;

    /**
     * Replaces expenditure list with the data in {@code expenditureList}.
     */
    void setExpenditureList(ReadOnlyExpenditureList expenditureList);

    /** Returns the ExpenditureList */
    ReadOnlyExpenditureList getExpenditureList();

    /**
     * Adds the given purchase.
     */
    void addPurchase(Purchase purchase);

    /** Returns an unmodifiable view of the filtered purchase list */
    ObservableList<Purchase> getFilteredPurchaseList();

    /**
     * Updates the filter of the filtered purchase list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPurchaseList(Predicate<Purchase> predicate);


    /**
     * Saves the current expenditure list state for undo/redo.
     */
    void commitExpenditureList();

    /**
     * Selected purchase in the filtered purchase list.
     * null if no purchase is selected.
     */
    ReadOnlyProperty<Purchase> selectedPurchaseProperty();

    /**
     * Returns the selected purchase in the filtered purchase list.
     * null if no purchase is selected.
     */
    Purchase getSelectedPurchase();

    /**
     * Sets the selected purchase in the filtered purchase list.
     */
    void setSelectedPurchase(Purchase purchase);


    void addWorkout(Workout workout);

    void commitWorkoutBook();

    void setSelectedWorkout(Workout workout);

    ReadOnlyProperty<Workout> selectedWorkoutProperty();

    ObservableList<Workout> getFilteredWorkoutList();

    ReadOnlyWorkoutBook getWorkoutList();

    void updateFilteredWorkoutList(Predicate<Workout> predicate);

    void setWorkoutBook(ReadOnlyWorkoutBook workoutBook);




}

package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.purchase.exceptions.PurchaseNotFoundException;
import seedu.address.model.task.Task;
import seedu.address.model.workout.Workout;

/**
 * Represents the in-memory model of the LIFE application data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedContactList versionedContactList;
    private final VersionedExpenditureList versionedExpenditureList;
    private final VersionedWorkoutBook versionedWorkoutBook;
    private final VersionedTaskList versionedTaskList;
    private final VersionedTaskList versionedTickedTaskList;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> filteredTickTasks;
    private final FilteredList<Purchase> filteredPurchases;
    private final FilteredList<Workout> filteredWorkout;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Task> selectedTask = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Purchase> selectedPurchase = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Workout> selectedWorkout = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given lifeApp and userPrefs.
     */
    public ModelManager(ReadOnlyContactList contactList, ReadOnlyUserPrefs userPrefs, ReadOnlyTaskList taskList,
                        ReadOnlyExpenditureList expenditureList, ReadOnlyWorkoutBook workoutBook) {
        super();
        requireAllNonNull(contactList, userPrefs, taskList, expenditureList, workoutBook);

        logger.fine("Initializing with contact list: " + contactList + " and user prefs " + userPrefs);
        versionedTaskList = new VersionedTaskList(taskList);
        versionedContactList = new VersionedContactList(contactList);
        versionedExpenditureList = new VersionedExpenditureList(expenditureList);
        versionedWorkoutBook = new VersionedWorkoutBook(workoutBook);
        versionedTickedTaskList = new VersionedTaskList(new TaskList());

        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedContactList.getPersonList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);
        filteredTasks = new FilteredList<>(versionedTaskList.getTaskList());
        filteredTickTasks = new FilteredList<>(versionedTickedTaskList.getTaskList());
        filteredWorkout = new FilteredList<>(versionedWorkoutBook.getWorkoutList());
        filteredPurchases = new FilteredList<>(versionedExpenditureList.getPurchaseList());
        filteredPurchases.addListener(this::ensureSelectedPurchaseIsValid);
    }

    public ModelManager() {
        this(new ContactList(), new UserPrefs(), new TaskList(), new ExpenditureList(), new WorkoutBook());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getContactListFilePath() {
        return userPrefs.getContactListFilePath();
    }

    @Override
    public void setContactListFilePath(Path contactListFilePath) {
        requireNonNull(contactListFilePath);
        userPrefs.setContactListFilePath(contactListFilePath);
    }

    //=========== ContactList ================================================================================

    @Override
    public void setContactList(ReadOnlyContactList contactList) {
        versionedContactList.resetData(contactList);
    }

    @Override
    public ReadOnlyContactList getContactList() {
        return versionedContactList;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedContactList.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedContactList.removePerson(target);

    }

    @Override
    public void addPerson(Person person) {
        versionedContactList.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        versionedContactList.setPerson(target, editedPerson);
    }
    //=========== task List ================================================================================

    @Override
    public void addTask(Task task) {
        versionedTaskList.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void addTickedTaskList(Task task) {
        task.addCompletedTag();
        versionedTickedTaskList.addTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        versionedTaskList.removeTask(target);
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return versionedTaskList;
    }

    @Override
    public ReadOnlyTaskList getTickedTaskList() {
        return versionedTickedTaskList;
    }



    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return versionedTaskList.hasTask(task);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        versionedTaskList.setTask(target, editedTask);
    }

    @Override
    public Task getSelectedTask() {
        return selectedTask.getValue();
    }

    @Override
    public void sortTask() {
        versionedTaskList.sortTask();
    }

    //======================================================================================================


    @Override
    public void setExpenditureList(ReadOnlyExpenditureList expenditureList) {
        versionedExpenditureList.resetData(expenditureList);
    }

    @Override
    public ReadOnlyExpenditureList getExpenditureList() {
        return versionedExpenditureList;
    }

    @Override
    public void addPurchase(Purchase purchase) {
        versionedExpenditureList.addPurchase(purchase);
        // updateFilteredPurhaseList(PREDICATE_SHOW_ALL_PURCHASES);
    }

    @Override
    public ObservableList<Purchase> getFilteredPurchaseList() {
        return filteredPurchases;
    }


    @Override
    public void updateFilteredPurchaseList(Predicate<Purchase> predicate) {
        requireNonNull(predicate);
        filteredPurchases.setPredicate(predicate);
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedContactList}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }



    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
    //=========== Filtered task List Accessors =============================================================

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public ObservableList<Task> getFilteredTickedTaskList() {
        return filteredTickTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTickedTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTickTasks.setPredicate(predicate);
    }

    //======================================================================================================

    @Override
    public void commitExpenditureList() {
        versionedExpenditureList.commit();
    }

    @Override
    public ReadOnlyProperty<Purchase> selectedPurchaseProperty() {
        return selectedPurchase;
    }

    @Override
    public Purchase getSelectedPurchase() {
        return selectedPurchase.getValue();
    }

    @Override
    public void setSelectedPurchase(Purchase purchase) {
        if (purchase != null && !filteredPurchases.contains(purchase)) {
            throw new PurchaseNotFoundException();
        }
        selectedPurchase.setValue(purchase);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoContactList() {
        return versionedContactList.canUndo();
    }

    @Override
    public boolean canRedoContactList() {
        return versionedContactList.canRedo();
    }

    @Override
    public void undoContactList() {
        versionedContactList.undo();
    }

    @Override
    public void redoContactList() {
        versionedContactList.redo();
    }

    @Override
    public void commitContactList() {
        versionedContactList.commit();
    }

    @Override
    public void commitTaskList() {
        versionedTaskList.commit();
    }

    @Override
    public void commitTickedTaskList() {
        versionedTickedTaskList.commit();
    }

    //=========== Selected ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public ReadOnlyProperty<Task> selectedTaskProperty() {
        return selectedTask;
    }

    @Override
    public Person getSelectedPerson() {
        return selectedPerson.getValue();
    }

    @Override
    public void setSelectedPerson(Person person) {
        if (person != null && !filteredPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    @Override
    public void setSelectedTask(Task task) {
        selectedTask.setValue(task);
    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    /**
     * Ensures {@code selectedPurchase} is a valid purchase in {@code filteredPurchases}.
     */
    private void ensureSelectedPurchaseIsValid(ListChangeListener.Change<? extends Purchase> change) {
        while (change.next()) {
            if (selectedPurchase.getValue() == null) {
                // null is always a valid selected purchase, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPurchaseReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPurchase.getValue());
            if (wasSelectedPurchaseReplaced) {
                // Update selectedPurchase to its new value.
                int index = change.getRemoved().indexOf(selectedPurchase.getValue());
                selectedPurchase.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPurchaseRemoved = change.getRemoved().stream()
                    .anyMatch(removedPurchase -> selectedPurchase.getValue().isSamePurchase(removedPurchase));
            if (wasSelectedPurchaseRemoved) {
                // Select the purchase that came before it in the list,
                // or clear the selection if there is no such purchase.
                selectedPurchase.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }
    //=================Workout Book========================================================================


    @Override
    public void setWorkoutBook(ReadOnlyWorkoutBook workoutBook) {
        versionedWorkoutBook.resetData(workoutBook);
    }

    @Override
    public ReadOnlyWorkoutBook getWorkoutList() {
        return versionedWorkoutBook;
    }

    @Override
    public void addWorkout(Workout workout) {
        versionedWorkoutBook.addWorkout(workout);
    }

    @Override
    public ObservableList<Workout> getFilteredWorkoutList() {
        return filteredWorkout;
    }

    @Override
    public void updateFilteredWorkoutList(Predicate<Workout> predicate) {
        requireNonNull(predicate);
        filteredWorkout.setPredicate(predicate);
    }
    @Override
    public void commitWorkoutBook() {
        versionedWorkoutBook.commit();
    }

    @Override
    public void setSelectedWorkout(Workout workout) {
        selectedWorkout.setValue(workout);
    }

    @Override
    public ReadOnlyProperty<Workout> selectedWorkoutProperty() {
        return selectedWorkout;
    }

    @Override
    public ArrayList<Workout> getRecent() {
        return versionedWorkoutBook.getRecent();
    }

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
        return versionedContactList.equals(other.versionedContactList)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

    //====================Common tasks =================================================

    /**
     * Checks for the validity of the input time string
     * @param string
     * @return a boolean indicating the validity
     */
    public static boolean isValidTime(String string) {
        DateFormat format = new SimpleDateFormat("HHmm");
        format.setLenient(false);
        try {
            format.parse(string);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks for the valid date
     * @param date an input string containing the date
     * @return a boolean indicating the validity of the input date string
     */
    public static boolean isValidDate(String date) {
        DateFormat format = new SimpleDateFormat("ddMMyy");
        format.setLenient(false);
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

}

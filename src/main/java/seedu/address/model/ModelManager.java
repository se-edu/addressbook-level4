package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
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
import seedu.address.model.task.Task;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.purchase.exceptions.PurchaseNotFoundException;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final VersionedExpenditureList versionedExpenditureList;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Purchase> filteredPurchases;
    private final VersionedTaskList versionedTaskList;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Task> selectedTask = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Purchase> selectedPurchase = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyTaskList taskList,
                        ReadOnlyExpenditureList expenditureList) {
        super();
        requireAllNonNull(addressBook, userPrefs, taskList, expenditureList);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);
        versionedTaskList = new VersionedTaskList(taskList);
        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedExpenditureList = new VersionedExpenditureList(expenditureList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);
        filteredTasks = new FilteredList<>(versionedTaskList.getTaskList());
        filteredPurchases = new FilteredList<>(versionedExpenditureList.getPurchaseList());
        filteredPurchases.addListener(this::ensureSelectedPurchaseIsValid);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new TaskList(), new ExpenditureList());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return versionedTaskList;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }


    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addTask(Task task){
        versionedTaskList.addTask(task);
//        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean hasTask(Task task){
        requireNonNull(task);
        return versionedTaskList.hasTask(task);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setTask(Task target, Task editedTask){
        requireAllNonNull(target, editedTask);

        versionedTaskList.setTask(target, editedTask);
    }

//=================Expenditure List========================================================================


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
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public ObservableList<Task> getFilteredTaskList(){
        return filteredTasks;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate){
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public void commitExpenditureList(){
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
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public void commitTaskList(){
        versionedTaskList.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public ReadOnlyProperty<Task> selectedTaskProperty(){
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
    public void setSelectedTask(Task task){
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

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
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

            boolean wasSelectedPurchaseReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
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
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}

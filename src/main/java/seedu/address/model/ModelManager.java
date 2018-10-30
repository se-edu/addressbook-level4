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
import seedu.address.commons.events.model.TranscriptChangedEvent;
import seedu.address.model.capgoal.CapGoal;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    //TODO: REMOVE
    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;

    //@@author alexkmj
    private final VersionedTranscript versionedTranscript;
    //@@author alexkmj
    private final FilteredList<Module> filteredModules;

    //@@author alexkmj
    /**
     * Initializes a ModelManager with the given transcript and userPrefs.
     */
    public ModelManager(ReadOnlyTranscript transcript, UserPrefs userPrefs) {
        super();
        requireAllNonNull(transcript, userPrefs);

        logger.fine("Initializing with transcript: " + transcript + " and user prefs " + userPrefs);

        versionedTranscript = new VersionedTranscript(transcript);
        filteredModules = new FilteredList<>(versionedTranscript.getModuleList());

        //TODO: REMOVE
        versionedAddressBook = new VersionedAddressBook(new AddressBook());
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
    }

    public ModelManager() {
        this(new Transcript(), new UserPrefs());
    }

    //@@author alexkmj
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     * TODO: REMOVE
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        versionedTranscript = new VersionedTranscript(new Transcript());
        filteredModules = new FilteredList<>(versionedTranscript.getModuleList());
    }

    @Override
    public void resetData(ReadOnlyTranscript newData) {
        versionedTranscript.resetData(newData);
        indicateTranscriptChanged();
    }

    //TODO: REMOVE
    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    //@@author alexkmj
    @Override
    public ReadOnlyTranscript getTranscript() {
        return versionedTranscript;
    }

    //@@author alexkmj
    /** Raises an event to indicate the model has changed */
    private void indicateTranscriptChanged() {
        raise(new TranscriptChangedEvent(versionedTranscript));
    }

    //@@author alexkmj
    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return versionedTranscript.hasModule(module);
    }

    //@@author alexkmj
    @Override
    public boolean hasMultipleInstances(Code code) {
        requireNonNull(code);
        return versionedTranscript.hasMultipleInstances(code);
    }

    //@@author alexkmj
    @Override
    public void deleteModule(Module target) {
        versionedTranscript.removeModule(target);
        indicateTranscriptChanged();
    }

    //@@author alexkmj
    @Override
    public void deleteModule(Predicate<Module> predicate) {
        versionedTranscript.removeModule(predicate);
        indicateTranscriptChanged();
    }

    @Override
    public void addModule(Module module) {
        versionedTranscript.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        indicateTranscriptChanged();
    }

    //@@author alexkmj
    @Override
    public void updateModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        versionedTranscript.updateModule(target, editedModule);
        indicateTranscriptChanged();
    }

    //=========== Filtered Module List Accessors =============================================================

    //@@author alexkmj
    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedTranscript}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return FXCollections.unmodifiableObservableList(filteredModules);
    }

    //@@author alexkmj
    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    //@@author alexkmj
    @Override
    public boolean canUndoTranscript() {
        return versionedTranscript.canUndo();
    }

    //@@author alexkmj
    @Override
    public boolean canRedoTranscript() {
        return versionedTranscript.canRedo();
    }

    //@@author alexkmj
    @Override
    public void undoTranscript() {
        versionedTranscript.undo();
        indicateTranscriptChanged();
    }

    //@@author alexkmj
    @Override
    public void redoTranscript() {
        versionedTranscript.redo();
        indicateTranscriptChanged();
    }

    //@@author alexkmj
    @Override
    public void commitTranscript() {
        versionedTranscript.commit();
    }

    //@@author jeremiah-ang
    @Override
    public CapGoal getCapGoal() {
        return versionedTranscript.getCapGoal();
    }

    //@@author jeremiah-ang
    @Override
    public void updateCapGoal(double capGoal) {
        versionedTranscript.setCapGoal(capGoal);
        indicateTranscriptChanged();
    }

    @Override
    public Module findModule(Module moduleToFind) {
        return versionedTranscript.findModule(moduleToFind);
    }

    @Override
    public Module findModule(Code moduleCodeToFind) {
        return versionedTranscript.findModule(moduleCodeToFind);
    }

    @Override
    public Module adjustModule(Module targetModule, Grade adjustGrade) {
        return versionedTranscript.adjustModule(targetModule, adjustGrade);
    }

    //@@author
    //TODO: REMOVE
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    //TODO: REMOVE
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    //TODO: REMOVE
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    //TODO: REMOVE
    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    //TODO: REMOVE
    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //TODO: REMOVE
    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     * TODO: REMOVE
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    //TODO: REMOVE
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    //TODO: REMOVE
    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    //TODO: REMOVE
    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    //TODO: REMOVE
    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    //TODO: REMOVE
    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    //TODO: REMOVE
    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public double getCap() {
        return versionedTranscript.getCurrentCap();
    }

    @Override
    public ObservableList<Module> getCompletedModuleList() {
        return versionedTranscript.getCompletedModuleList();
    }

    @Override
    public ObservableList<Module> getIncompleteModuleList() {
        return versionedTranscript.getIncompleteModuleList();
    }

    //@@author alexkmj
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
            && filteredPersons.equals(other.filteredPersons) // TODO: REMOVE
            && filteredModules.equals(other.filteredModules);
    }
}

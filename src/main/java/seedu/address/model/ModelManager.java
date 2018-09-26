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

    private final VersionedTranscript versionedTranscript;
    private final FilteredList<Module> filteredModules;

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

    @Override
    public ReadOnlyTranscript getTranscript() {
        return versionedTranscript;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTranscriptChanged() {
        raise(new TranscriptChangedEvent(versionedTranscript));
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return versionedTranscript.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        versionedTranscript.removeModule(target);
        indicateTranscriptChanged();
    }

    @Override
    public void addModule(Module module) {
        versionedTranscript.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        indicateTranscriptChanged();
    }

    @Override
    public void updateModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        versionedTranscript.updateModule(target, editedModule);
        indicateTranscriptChanged();
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedTranscript}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return FXCollections.unmodifiableObservableList(filteredModules);
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoTranscript() {
        return versionedTranscript.canUndo();
    }

    @Override
    public boolean canRedoTranscript() {
        return versionedTranscript.canRedo();
    }

    @Override
    public void undoTranscript() {
        versionedTranscript.undo();
        indicateTranscriptChanged();
    }

    @Override
    public void redoTranscript() {
        versionedTranscript.redo();
        indicateTranscriptChanged();
    }

    @Override
    public void commitTranscript() {
        versionedTranscript.commit();
    }

    @Override
    public double getCapGoal() {
        return versionedTranscript.getCapGoal();
    }

    @Override
    public void updateCapGoal(double capGoal) {
        versionedTranscript.setCapGoal(capGoal);
        indicateTranscriptChanged();
    }

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

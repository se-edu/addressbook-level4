package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.model.module.Module;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true TODO: REMOVE*/
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Clears existing backing model and replaces with the newly provided data.
     * @param replacement the replacement.
     */
    void resetData(ReadOnlyTranscript replacement);

    /** Clears existing backing model and replaces with the provided new data. TODO: REMOVE*/
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the Transcript.
     *
     * @return read only version of the transcript
     */
    ReadOnlyTranscript getTranscript();

    /**
     * Returns true if a module with the same identity as {@code module} exists in the transcript.
     *
     * @param module module to find in the transcript
     * @return true if module exists in transcript
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given module.
     * <p>
     * The module must exist in the transcript.
     *
     * @param target module to be deleted from the transcript
     */
    void deleteModule(Module target);

    /**
     * Adds the given module.
     * <p>
     * {@code module} must not already exist in the transcript.
     *
     * @param module module to be added into the transcript
     */
    void addModule(Module module);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the transcript. The module identity of {@code editedModule}
     * must not be the same as another existing module in the transcript.
     *
     * @param target module to be updated
     * @param editedModule the updated version of the module
     */
    void updateModule(Module target, Module editedModule);

    /**
     * Returns an unmodifiable view of the filtered module list.
     */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Returns true if the model has previous transcript states to restore.
     */
    boolean canUndoTranscript();

    /**
     * Returns true if the model has undone transcript states to restore.
     */
    boolean canRedoTranscript();

    /**
     * Restores the model's transcript to its previous state.
     */
    void undoTranscript();

    /**
     * Restores the model's transcript to its previously undone state.
     */
    void redoTranscript();

    /**
     * Saves the current transcript state for undo/redo.
     */
    void commitTranscript();

    /**
     * Get the cap goal of the current transcript
     */
    double getCapGoal();

    /**
     * Set the cap goal of the current transcript
     */
    void updateCapGoal(double capGoal);



    /** Returns the AddressBook TODO: REMOVE*/
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     * TODO: REMOVE
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     * TODO: REMOVE
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     * TODO: REMOVE
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person
     * in the address book.
     * TODO: REMOVE
     */
    void updatePerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list TODO: REMOVE */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     * TODO: REMOVE
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     * TODO: REMOVE
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     * TODO: REMOVE
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     * TODO: REMOVE
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     * TODO: REMOVE
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     * TODO: REMOVE
     */
    void commitAddressBook();
}

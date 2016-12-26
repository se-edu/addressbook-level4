package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Person toAdd) throws DuplicatePersonException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Updates the person in the list at position {@code index} with {@code updatedPerson}.
     *
     * @throws DuplicatePersonException if editing the person's details causes the person to
     *      be equivalent to another existing person in the list.
     */
    public void updatePerson(int index, ReadOnlyPerson updatedPerson)
            throws DuplicatePersonException {
        assert index >= 0;
        assert updatedPerson != null;

        Person personToUpdate = internalList.get(index);
        if (!personToUpdate.equals(updatedPerson)
                && internalList.contains(updatedPerson)) {
            throw new DuplicatePersonException();
        }

        personToUpdate.updateDetailsWith(updatedPerson);
        // set item in list so that observers of the list are notified of the change
        internalList.set(index, personToUpdate);
    }

    /**
     * Updates the person {@code personToUpdate} with {@code updatedPerson}.
     *
     * @throws DuplicatePersonException if editing the person's details causes the person to
     *      be equivalent to another existing person in the list.
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void updatePerson(ReadOnlyPerson personToUpdate, ReadOnlyPerson updatedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        assert personToUpdate != null;
        assert updatedPerson != null;

        int index = internalList.indexOf(personToUpdate);
        if (index < 0) {
            throw new PersonNotFoundException();
        }

        updatePerson(index, updatedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        assert toRemove != null;
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        final UniquePersonList replacement = new UniquePersonList();
        for (final ReadOnlyPerson person : persons) {
            replacement.add(new Person(person));
        }
        setPersons(replacement);
    }

    public UnmodifiableObservableList<Person> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && this.internalList.equals(
                ((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePersonException extends DuplicateDataException {
        protected DuplicatePersonException() {
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class PersonNotFoundException extends Exception {}

}

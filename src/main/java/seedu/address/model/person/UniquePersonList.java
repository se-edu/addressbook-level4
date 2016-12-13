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
     * Edits the details of an existing person in the list.
     *
     * @param dst person to edit
     * @param src person with updated details
     * @throws DuplicatePersonException if editing the person's details causes the person to
     *      be equivalent to another existing person in the list.
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void updatePerson(Person dst, ReadOnlyPerson src)
            throws DuplicatePersonException, PersonNotFoundException {
        assert dst != null;
        assert src != null;

        if (isDuplicatePerson(dst, src)) {
            throw new DuplicatePersonException();
        }

        int index = internalList.indexOf(dst);
        if (index < 0) {
            throw new PersonNotFoundException();
        }

        editDetails(dst, src);
        internalList.set(index, dst);
    }

    /**
     * Verifies if editing the person's details causes the person to
     * be equivalent to another existing person in the list.
     *
     * @return true if editing the person's details causes the person to
     *      be equivalent to another existing person in the list.
     */
    private boolean isDuplicatePerson(Person dst, ReadOnlyPerson src) {
        assert dst != null;
        assert src != null;

        List<Person> listCopy = new ArrayList<>(internalList);
        listCopy.remove(dst);

        return listCopy.contains(src);
    }

    /**
     * Updates the values of the person {@code dst}.
     *
     * @param dst person to edit
     * @param src person with updated details
     */
    private void editDetails(Person dst, ReadOnlyPerson src) {
        assert dst != null;
        assert src != null;

        dst.setName(src.getName());
        dst.setPhone(src.getPhone());
        dst.setEmail(src.getEmail());
        dst.setAddress(src.getAddress());
        dst.setTags(src.getTags());
    }

    /**
     * Returns the {@code Person} object equivalent to {@code ReadOnlyPerson readOnlyPerson}.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public Person findPersonInList(ReadOnlyPerson readOnlyPerson) throws PersonNotFoundException {
        assert readOnlyPerson != null;

        int indexOfPerson = internalList.indexOf(readOnlyPerson);

        if (indexOfPerson < 0) {
            throw new PersonNotFoundException();
        }

        return internalList.get(indexOfPerson);
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

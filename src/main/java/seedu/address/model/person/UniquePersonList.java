package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.DuplicateDataException;

import java.util.*;
import java.util.stream.Collectors;

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
     * Removes all persons specified by {@code personsToRemove} from this list.<br>
     * @throws PersonNotFoundException if any person can't be found in this list.
     */
    public void removeAll(Collection<ReadOnlyPerson> personsToRemove) throws PersonNotFoundException {
        assert !CollectionUtil.isAnyNull(Arrays.asList(personsToRemove));

        final Collection<ReadOnlyPerson> missingPersons =
                personsToRemove.stream().filter(p -> !contains(p)).collect(Collectors.toList());
        if (!missingPersons.isEmpty()) {
            throw new PersonNotFoundException(missingPersons);
        }

        internalList.removeAll(personsToRemove);
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
    public static class PersonNotFoundException extends Exception {

        private Collection<ReadOnlyPerson> missingPersons = Collections.emptyList();

        protected PersonNotFoundException(Collection<ReadOnlyPerson> missingPersons) {
            super("Person(s) not found in list:\n" + StringUtil.toIndexedListString(missingPersons));
            this.missingPersons = missingPersons;
        }

        public Collection<ReadOnlyPerson> getPersons() {
            return missingPersons;
        }

    }

}

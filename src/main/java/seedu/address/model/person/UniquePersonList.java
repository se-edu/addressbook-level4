package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.Utils;
import seedu.address.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of persons that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations needed for the app's features.
 *
 * @see Person#equals(Object)
 * @see Utils#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Person> {

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

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniquePersonList() {}

    /**
     * Varargs/array constructor, enforces no nulls or duplicates.
     */
    public UniquePersonList(Person... persons) throws DuplicatePersonException {
        Utils.assertNotNull(persons);
        final List<Person> initialPersons = Arrays.asList(persons);
        if (!Utils.elementsAreUnique(initialPersons)) {
            throw new DuplicatePersonException();
        }
        internalList.addAll(initialPersons);
    }

    /**
     * java collections constructor, enforces no null or duplicate elements.
     */
    public UniquePersonList(Collection<Person> persons) throws DuplicatePersonException {
        Utils.assertNoNullElements(persons);
        if (!Utils.elementsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }
        internalList.addAll(persons);
    }

    /**
     * java set constructor, enforces no nulls.
     */
    public UniquePersonList(Set<Person> persons) {
        Utils.assertNoNullElements(persons);
        internalList.addAll(persons);
    }

    /**
     * Copy constructor, insulates from changes in source.
     */
    public UniquePersonList(UniquePersonList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * Unmodifiable java List view with elements cast as immutable {@link ReadOnlyPerson}s.
     * For use with other methods/libraries.
     * Any changes to the internal list/elements are immediately visible in the returned list.
     */
    public List<ReadOnlyPerson> immutableListView() {
        return Collections.unmodifiableList(internalList);
    }

    /**
     * All persons in this list as a Set. This set is mutable and change-insulated against the internal list.
     */
    public Set<Person> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Checks if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        Utils.assertNotNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Person toAdd) throws DuplicatePersonException {
        Utils.assertNotNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        Utils.assertNotNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    /**
     * Clears all persons in list.
     */
    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    public ObservableList<Person> getInternalList() {
        return internalList;
    }

}

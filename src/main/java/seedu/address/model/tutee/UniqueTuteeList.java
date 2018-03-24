package seedu.address.model.tutee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTuteeList implements Iterable<Tutee> {
    private final ObservableList<Tutee> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent tutee as the given argument.
     */
    public boolean contains(Tutee toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a tutee to the list.
     *
     * @throws DuplicatePersonException if the tutee to add is a duplicate of an existing tutee in the list.
     */
    public void add(Tutee toAdd) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the tutee {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(Tutee target, Tutee editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedPerson) && internalList.contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent tutee from the list.
     *
     * @throws PersonNotFoundException if no such tutee could be found in the list.
     */
    public boolean remove(Tutee toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean tuteeFoundAndDeleted = internalList.remove(toRemove);
        if (!tuteeFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return tuteeFoundAndDeleted;
    }

    public void setTutees(UniqueTuteeList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTutees(List<Person> persons) throws DuplicatePersonException {
        requireAllNonNull(persons);
        final UniqueTuteeList replacement = new UniqueTuteeList();
        for (final Person person : persons) {
            if (person instanceof Tutee) {
                replacement.add((Tutee) person);
            }
        }
        setTutees(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tutee> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Tutee> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTuteeList // instanceof handles nulls
                && this.internalList.equals(((UniqueTuteeList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

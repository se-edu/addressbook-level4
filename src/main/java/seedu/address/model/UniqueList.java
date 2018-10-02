package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A generic list of type that extends from Entity class that enforces uniqueness between its elements
 * and does not allow nulls.
 * An element is considered unique by comparing using {@code Entity#isSame(Object)}.
 * As such, adding and updating of elements uses Entity#isSame(Object) for equality so as to ensure that
 * the element being added or updated is unique in terms of identity in the UniqueList.
 * However, the removal of a element uses equal function
 * to ensure that the element with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Entity#isSame(Object)
 */
public class UniqueList<T extends Entity> implements Iterable<T> {

    private final ObservableList<T> internalList;

    public UniqueList() {
        this.internalList = FXCollections.observableArrayList();
    }

    /**
     * Returns true if the list contains an equivalent element as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSame);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setElement(T target, T edited) {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSame(edited) && contains(edited)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, edited);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setElements(UniqueList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code T}.
     * {@code T} must not contain duplicate elements.
     */
    public void setElements(List<T> elements) {
        requireAllNonNull(elements);
        if (!elementsAreUnique(elements)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(elements);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueList// instanceof handles nulls
                && internalList.equals(((UniqueList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code T} contains only unique elements.
     */
    private boolean elementsAreUnique(List<T> elements) {
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                if (elements.get(i).isSame(elements.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}


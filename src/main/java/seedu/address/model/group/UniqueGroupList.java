package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of Groups that enforces uniqueness between its elements and does not allow nulls.
 * A Group is considered unique by comparing using {@code Group#isSameGroup(Group)}. As such, adding and updating of
 * Groups uses Group#isSameGroup(Group) for equality so as to ensure that the Group being added or updated is
 * unique in terms of identity in the UniqueGroupList. However, the removal of a Group uses Group#equals(Object) so
 * as to ensure that the Group with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Group#isSameGroup(Group) - coming sv1.2
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Adds a Group to the list.
     * The Group must not already exist in the list.
     */
    public void add(Group toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent Group from the list.
     * The Group must exist in the list.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code groups}.
     * {@code groups} must not contain duplicate group. - coming v1.2
     */
    public void setGroups(List<Group> groups) {
        requireAllNonNull(groups);
        internalList.setAll(groups);
    }

    public int numberOfGroups() { return internalList.size(); }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && internalList.equals(((UniqueGroupList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

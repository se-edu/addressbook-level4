package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.leave.exceptions.DuplicateLeaveException;
import seedu.address.model.leave.exceptions.LeaveNotFoundException;

/**
 * A list of leaves that enforces uniqueness between its elements and does not allow nulls.
 * A leave is considered unique by comparing using {@code Leave#isSameRequest(Leave)}. As such, adding and updating of
 * leaves uses Leave#isSameRequest(Leave) for equality so as to ensure that the leave being added or updated is
 * unique in terms of identity in the UniqueLeaveList. However, the removal of a Leave uses Leaves#equals(Object) so
 * as to ensure that the leave with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Leave#isSameRequest(Leave)
 */
public class UniqueLeaveList implements Iterable<Leave> {

    private final ObservableList<Leave> internalList2 = FXCollections.observableArrayList();


    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Leave toCheck) {
        requireNonNull(toCheck);
        return internalList2.stream().anyMatch(toCheck::isSameRequest);
    }

    /**
     * Adds a leave to the list.
     * The leave must not already exist in the list.
     */
    public void add(Leave toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLeaveException();
        }
        internalList2.add(toAdd);
    }

    /**
     * Replaces the leave {@code target} in the list with {@code editedLeave}.
     * {@code target} must exist in the list.
     * The leave identity of {@code editedLeave} must not be the same as another existing leave in the list.
     */
    public void setRequest(Leave target, Leave editedRequest) {
        requireAllNonNull(target, editedRequest);

        int index = internalList2.indexOf(target);
        if (index == -1) {
            throw new LeaveNotFoundException();
        }

        if (!target.isSameRequest(editedRequest) && contains(editedRequest)) {
            throw new DuplicateLeaveException();
        }

        internalList2.set(index, editedRequest);
    }

    /**
     * Removes the equivalent leave from the list.
     * The leave must exist in the list.
     */
    public void remove(Leave toRemove) {
        requireNonNull(toRemove);
        if (!internalList2.remove(toRemove)) {
            throw new LeaveNotFoundException();
        }
    }

    public void setLeaves(UniqueLeaveList replacement) {
        requireNonNull(replacement);
        internalList2.setAll(replacement.internalList2);
    }

    /**
     * Replaces the contents of this list with {@code leaves}.
     * {@code leaves} must not contain duplicate leaves.
     */
    public void setLeaves(List<Leave> requests) {
        requireAllNonNull(requests);
        if (!leavesAreUnique(requests)) {
            throw new DuplicateLeaveException();
        }

        internalList2.setAll(requests);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Leave> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList2);
    }

    @Override
    public Iterator<Leave> iterator() {
        return internalList2.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLeaveList// instanceof handles nulls
                && internalList2.equals(((UniqueLeaveList) other).internalList2));
    }

    @Override
    public int hashCode() {
        return internalList2.hashCode();
    }

    /**
     * Returns true if {@code leaves} contains only unique leaves.
     */
    private boolean leavesAreUnique(List<Leave> requests) {
        for (int i = 0; i < requests.size() - 1; i++) {
            for (int j = i + 1; j < requests.size(); j++) {
                if (requests.get(i).isSameRequest(requests.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }




}

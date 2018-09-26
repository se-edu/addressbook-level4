package seedu.address.model.leave;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.leave.exceptions.DuplicateLeaveException;
import seedu.address.model.leave.exceptions.LeaveNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueLeaveList implements Iterable<Leave>{

    private final ObservableList<Leave> internalList2 = FXCollections.observableArrayList();


    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Leave toCheck) {
        requireNonNull(toCheck);
        return internalList2.stream().anyMatch(toCheck::isSameRequest);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Leave toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLeaveException();
        }
        internalList2.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
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
     * Removes the equivalent person from the list.
     * The person must exist in the list.
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
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
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
    public Iterator<Leave> iterator() { return internalList2.iterator();
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
     * Returns true if {@code persons} contains only unique persons.
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

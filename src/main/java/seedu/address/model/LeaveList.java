package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.UniqueLeaveList;

/**
 * Wraps all data at the leave-list level
 * Duplicates are not allowed (by .isSameRequest comparison)
 */
public class LeaveList implements ReadOnlyLeaveList {

    private final UniqueLeaveList requests;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        requests = new UniqueLeaveList();
    }

    public LeaveList() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public LeaveList(ReadOnlyLeaveList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setLeaves(List<Leave> requests) {
        this.requests.setLeaves(requests);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyLeaveList newData) {
        requireNonNull(newData);

        setLeaves(newData.getRequestList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasRequest(Leave request) {
        requireNonNull(request);
        return requests.contains(request);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addRequest(Leave p) {
        requests.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updateRequest(Leave target, Leave editedLeave) {
        requireNonNull(editedLeave);

        requests.setRequest(target, editedLeave);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeRequest(Leave key) { requests.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return requests.asUnmodifiableObservableList().size() + " requests";
        // TODO: refine later
    }

   @Override
   public ObservableList<Leave> getRequestList(){ return  requests.asUnmodifiableObservableList();}



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveList // instanceof handles nulls
                && requests.equals(((LeaveList) other).requests));
    }

    @Override
    public int hashCode() {
        return requests.hashCode();
    }
}

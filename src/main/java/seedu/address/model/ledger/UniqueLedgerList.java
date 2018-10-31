package seedu.address.model.ledger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import seedu.address.model.ledger.exceptions.DuplicateLedgerException;
import seedu.address.model.ledger.exceptions.LedgerNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of ledgers that enforces uniqueness between its elements and does not allow nulls.
 * A ledger is considered unique by comparing using {@code Ledger#isSameLedger(Ledger)}. As such, adding and updating of
 * ledger uses Ledger#isSameLedger(Ledger) for equality so as to ensure that the ledger being added or updated is
 * unique in terms of identity in the UniqueLedgerList. However, the removal of a member uses Ledger#equals(Object) so
 * as to ensure that the member with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Ledger#isSameLedger(Ledger)
 */
public class UniqueLedgerList implements Iterable<Ledger> {

    private final ObservableList<Ledger> internalList = FXCollections.observableArrayList();
    private final ObservableSet<Ledger> internalList2 = FXCollections.observableSet();

    /**

     * Returns true if the list contains an equivalent ledger as the given argument.
     */
    public boolean contains(Ledger toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameLedger);
    }

    /**
     * Adds a ledger to the list.
     * If the ledger date is found in the list, duplicate ledger exception will be thrown
     */
    public void add(Ledger toAdd) {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateLedgerException();
        }
        internalList.add(toAdd);
        internalList2.add(toAdd);
    }

    public void setDate(Ledger target, Ledger editedDate) {
        requireAllNonNull(target, editedDate);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LedgerNotFoundException();
        }

        if (contains(editedDate)) {
            //add or deduct balance from ledger;
        }
        internalList.set(index, editedDate);
    }

    /**
     * Removes the equivalent ledger from the list.
     * The ledger must exist in the list.
     */
    public void remove(Ledger toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LedgerNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setLedgers(UniqueLedgerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setLedgers(List<Ledger> ledgers) {
        requireAllNonNull(ledgers);
        if (!ledgersAreUnique(ledgers)) {
            throw new DuplicateLedgerException();
        }

        internalList.setAll(ledgers);
    }

    /**
     * Replaces the ledger {@code target} in the list with {@code editedLedger}.
     * {@code target} must exist in the list.
     * The DateLedger of {@code editedLedger} must not be the same as another existing ledger in the list.
     */
    public void setLedger(Ledger target, Ledger editedLedger) {
        requireAllNonNull(target, editedLedger);
        DateLedger dateTarget = target.getDateLedger();
        int index = -1;
        for (int i = 0; i < internalList.size(); i++) {
            if (dateTarget.getDate().equals(internalList.get(i).getDateLedger().getDate())) {
                index = i;
            }
        }
        if (index == -1) {
            throw new LedgerNotFoundException();
        }
        if (!target.isSameLedger(editedLedger) && contains(editedLedger)) {
            throw new DuplicateLedgerException();
        }

        internalList.set(index, editedLedger);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Ledger> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    public ObservableSet<Ledger> asUnmodifiableObservableSet() {
        return FXCollections.unmodifiableObservableSet(internalList2);
    }

    @Override
    public Iterator<Ledger> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLedgerList // instanceof handles nulls
                        && internalList.equals(((UniqueLedgerList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code ledgers} contains only unique ledgers.
     */
    private boolean ledgersAreUnique(List<Ledger> ledgers) {
        for (int i = 0; i < ledgers.size() - 1; i++) {
            for (int j = i + 1; j < ledgers.size(); j++) {
                if (ledgers.get(i).isSameLedger(ledgers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

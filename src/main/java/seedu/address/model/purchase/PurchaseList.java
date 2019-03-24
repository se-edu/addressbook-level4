package seedu.address.model.purchase;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.purchase.exceptions.PurchaseNotFoundException;

/**
 * A list of purchases that does not allow nulls.
 */

public class PurchaseList implements Iterable<Purchase> {
    private final ObservableList<Purchase> internalList = FXCollections.observableArrayList();
    private final ObservableList<Purchase> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Purchase toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePurchase);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Purchase toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the purchase {@code target} in the list with {@code editedPurchase}.
     * {@code target} must exist in the list.
     */
    public void setPurchase(Purchase target, Purchase editedPurchase) {
        requireAllNonNull(target, editedPurchase);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PurchaseNotFoundException();
        }

        internalList.set(index, editedPurchase);
    }

    /**
     * Removes the equivalent purchase from the list.
     * The purchase must exist in the list.
     */
    public void remove(Purchase toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PurchaseNotFoundException();
        }
    }

    public void setPurchases(PurchaseList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPurchases(List<Purchase> purchases) {
        requireAllNonNull(purchases);
        internalList.setAll(purchases);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Purchase> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Purchase> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PurchaseList // instanceof handles nulls
                && internalList.equals(((PurchaseList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

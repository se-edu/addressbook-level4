package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.purchase.Purchase;


/**
 * Unmodifiable view of the expenditure list
 */
public interface ReadOnlyExpenditureList extends Observable {

    /**
     * Returns an unmodifiable view of the purchase list.
     */
    ObservableList<Purchase> getPurchaseList();

}

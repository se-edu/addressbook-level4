package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENTERTAINMENT;
import static seedu.address.testutil.TypicalPurchases.PRAWNMEE;
import static seedu.address.testutil.TypicalPurchases.getTypicalExpenditureList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.purchase.Purchase;
import seedu.address.testutil.PurchaseBuilder;

public class ExpenditureListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ExpenditureList expenditureList = new ExpenditureList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), expenditureList.getPurchaseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyExpenditureList_replacesData() {
        ExpenditureList newData = getTypicalExpenditureList();
        expenditureList.resetData(newData);
        assertEquals(newData, expenditureList);
    }



    @Test
    public void getPurchaseList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        expenditureList.getPurchaseList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        expenditureList.addListener(listener);
        expenditureList.addPurchase(PRAWNMEE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        expenditureList.addListener(listener);
        expenditureList.removeListener(listener);
        expenditureList.addPurchase(PRAWNMEE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyExpenditureList whose purchases list can violate interface constraints.
     */
    private static class ExpenditureListStub implements ReadOnlyExpenditureList {
        private final ObservableList<Purchase> purchases = FXCollections.observableArrayList();

        ExpenditureListStub(Collection<Purchase> purchases) {
            this.purchases.setAll(purchases);
        }

        @Override
        public ObservableList<Purchase> getPurchaseList() {
            return purchases;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }
}

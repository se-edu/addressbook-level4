package seedu.address.testutil;

import seedu.address.model.ExpenditureList;
import seedu.address.model.purchase.Purchase;

/**
 * A utility class to help with building ExpenditureList objects.
 * Example usage: <br>
 *     {@code ExpenditureList expl = new ExpenditureListBuilder().withPurchase("IceCream", "Movie").build();}
 */
public class ExpenditureListBuilder {
    private ExpenditureList expenditureList;

    public ExpenditureListBuilder() {
        expenditureList = new ExpenditureList();
    }

    public ExpenditureListBuilder(ExpenditureList expenditureList) {
        this.expenditureList = expenditureList;
    }

    /**
     * Adds a new {@code Purchase} to the {@code ExpenditureList} that we are building.
     */
    public ExpenditureListBuilder withPurchase(Purchase purchase) {
        expenditureList.addPurchase(purchase);
        return this;
    }

    public ExpenditureList build() {
        return expenditureList;
    }
}

package seedu.address.model.purchase;

//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PURCHASENAME_PRAWNMEE;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_PRAWNMEE;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOOD;
//import static seedu.address.testutil.TypicalPurchases.MCFLURRY;
//import static seedu.address.testutil.TypicalPurchases.PRAWNMEE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.PurchaseBuilder;

public class PurchaseTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Purchase purchase = new PurchaseBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        purchase.getTags().remove(0);
    }
}

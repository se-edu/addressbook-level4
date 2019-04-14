package seedu.address.model.purchase;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_MOVIE;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENTERTAINMENT;
import static seedu.address.testutil.TypicalPurchases.MCFLURRY;
import static seedu.address.testutil.TypicalPurchases.MOVIE;

//import java.util.Arrays;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//import seedu.address.model.purchase.exceptions.PurchaseNotFoundException;
//import seedu.address.testutil.PurchaseBuilder;

public class PurchaseListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PurchaseList purchaseList = new PurchaseList();

    @Test
    public void contains_nullPurchase_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        purchaseList.contains(null);
    }

    @Test
    public void add_nullPurchase_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        purchaseList.add(null);
    }

    @Test
    public void setPurchase_nullTargetPurchase_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        purchaseList.setPurchase(null, MCFLURRY);
    }


    @Test
    public void setPurchases_nullPurchaseList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        purchaseList.setPurchases((PurchaseList) null);
    }

    @Test
    public void setPurchases_purchaseList_replacesOwnListWithProvidedPurchaseList() {
        purchaseList.add(MCFLURRY);
        PurchaseList expectedPurchaseList = new PurchaseList();
        expectedPurchaseList.add(MOVIE);
        purchaseList.setPurchases(expectedPurchaseList);
        assertEquals(expectedPurchaseList, purchaseList);
    }

    @Test
    public void setPurchases_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        purchaseList.setPurchases((List<Purchase>) null);
    }

    @Test
    public void setPurchases_list_replacesOwnListWithProvidedList() {
        purchaseList.add(MCFLURRY);
        List<Purchase> purchasesList = Collections.singletonList(MOVIE);
        //used purchasesList as purchaseList has been used
        purchaseList.setPurchases(purchasesList);
        PurchaseList expectedPurchaseList = new PurchaseList();
        expectedPurchaseList.add(MOVIE);
        assertEquals(expectedPurchaseList, purchaseList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        purchaseList.asUnmodifiableObservableList().remove(0);
    }


    /*

    @Test
    public void contains_purchaseNotInList_returnsFalse() {
        assertFalse(purchaseList.contains(MCFLURRY));
    }

    @Test
    public void contains_purchaseInList_returnsTrue() {
        purchaseList.add(MCFLURRY);
        assertTrue(purchaseList.contains(MCFLURRY));
    }

    @Test
    public void contains_purchaseWithSameIdentityFieldsInList_returnsTrue() {
        purchaseList.add(MCFLURRY);
        Purchase editedMcflurry = new PurchaseBuilder(MCFLURRY).withPrice(VALID_PRICE_MOVIE)
        .withTags(VALID_TAG_ENTERTAINMENT).build();
        assertTrue(purchaseList.contains(editedMcflurry));
    }

    @Test
    public void setPurchase_targetPurchaseNotInList_throwsPurchaseNotFoundException() {
        thrown.expect(PurchaseNotFoundException.class);
        purchaseList.setPurchase(MCFLURRY, MCFLURRY);
    }


    @Test
    public void remove_nullPurchase_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        purchaseList.remove(null);
    }

    @Test
    public void remove_purchaseDoesNotExist_throwsPurchaseNotFoundException() {
        thrown.expect(PurchaseNotFoundException.class);
        purchaseList.remove(MCFLURRY);
    }

    @Test
    public void remove_existingPurchase_removesPurchase() {
        purchaseList.add(MCFLURRY);
        purchaseList.remove(MCFLURRY);
        PurchaseList expectedPurchaseList = new PurchaseList();
        assertEquals(expectedPurchaseList, purchaseList);
    }
    */
}

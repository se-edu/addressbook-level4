package seedu.address.model.purchase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PurchaseNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PurchaseName(null));
    }

    @Test
    public void constructor_invalidPurchaseName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PurchaseName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> PurchaseName.isValidName(null));

        // invalid name
        assertFalse(PurchaseName.isValidName("")); // empty string
        assertFalse(PurchaseName.isValidName(" ")); // spaces only
        assertFalse(PurchaseName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(PurchaseName.isValidName("ice cream!")); // contains non-alphanumeric characters

        // valid name
        assertTrue(PurchaseName.isValidName("ice cream")); // alphabets only
        assertTrue(PurchaseName.isValidName("51")); // numbers only
        assertTrue(PurchaseName.isValidName("bicycle rental for 3hrs")); // alphanumeric characters
        assertTrue(PurchaseName.isValidName("Mcdonald")); // with capital letters
        assertTrue(PurchaseName.isValidName("bicycle rental at east coast park with family for 3hrs")); // long names
    }
}

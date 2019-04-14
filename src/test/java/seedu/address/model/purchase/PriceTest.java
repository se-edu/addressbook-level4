package seedu.address.model.purchase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid prices
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("20")); // no decimal point
        assertFalse(Price.isValidPrice("20.")); // decimal point with no numbers after
        assertFalse(Price.isValidPrice("2.0")); // less than 2 numbers after decimal point
        assertFalse(Price.isValidPrice("2.023")); // more than 2 numbers after decimal point
        assertFalse(Price.isValidPrice(".20")); // no numbers before decimal point
        assertFalse(Price.isValidPrice("price")); // non-numeric
        assertFalse(Price.isValidPrice("$1.50")); // '$' before digits
        assertFalse(Price.isValidPrice("100.00!")); // '!' after digits
        assertFalse(Price.isValidPrice("1,000.00")); // commas within digits
        assertFalse(Price.isValidPrice("20 000.00")); // spaces within digits

        // valid prices
        assertTrue(Price.isValidPrice("20.50")); // exactly 2 numbers after decimal point
        assertTrue(Price.isValidPrice("0.90"));
        assertTrue(Price.isValidPrice("98000000000000.00")); // long prices
    }
}

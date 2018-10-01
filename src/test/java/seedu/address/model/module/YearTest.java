package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class YearTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void constructorInvalidYearThrowsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Year(0));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Year("0"));
    }

    @Test
    public void isValidYear() {
        // invalid year format
        assertFalse(Year.isValidYear(0)); // year must be at least 1
        assertFalse(Year.isValidYear(6)); // year must be 5 or below
        assertFalse(Year.isValidYear(10)); // only 1 digit allowed

        // valid year format
        assertTrue(Year.isValidYear(1)); // year 1
        assertTrue(Year.isValidYear(2)); // year 2
        assertTrue(Year.isValidYear(3)); // year 3
        assertTrue(Year.isValidYear(4)); // year 4
        assertTrue(Year.isValidYear(5)); // year 5
    }

    @Test
    public void toStringValid() {
        assertTrue(new Year(1).toString().contentEquals("1"));
    }

    @Test
    public void equalsValid() {
        assertTrue(new Year(1).equals(new Year(1)));
    }

    @Test
    public void hashCodeValid() {
        assertTrue(new Year(1).hashCode() == "1".hashCode());
    }
}

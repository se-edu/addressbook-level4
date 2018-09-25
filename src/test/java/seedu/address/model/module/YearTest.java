package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class YearTest {

    @Test
    public void constructorInvalidYearThrowsIllegalArgumentException() {
        int invalidYear = 1;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Year(invalidYear));
    }

    @Test
    public void isValidYear() {
        // invalid year format
        assertFalse(Year.isValidYear(1)); // single digit
        assertFalse(Year.isValidYear(10)); // two digit
        assertFalse(Year.isValidYear(100)); // three digit
        assertFalse(Year.isValidYear(1000)); // third digit must be either 1 or 2

        // valid year
        assertTrue(Year.isValidYear(1010)); // first digit and third digit are both 1
        assertTrue(Year.isValidYear(1020)); // first digit is 1 and third digit is 2
        assertTrue(Year.isValidYear(2020)); // first digit and third digit are both 2
        assertTrue(Year.isValidYear(2010)); // first digit is 2 and third digit is 1
    }

    @Test
    public void toStringValid() {
        assertTrue(new Year(1819).toString().contentEquals("1819"));
    }

    @Test
    public void equalsValid() {
        assertTrue(new Year(1819).equals(new Year(1819)));
    }
}

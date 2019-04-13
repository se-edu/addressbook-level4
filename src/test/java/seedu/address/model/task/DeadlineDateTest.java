package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DeadlineDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DeadlineDate(null));
    }

    @Test
    public void constructor_invalidDeadlineDate_throwsIllegalArgumentException() {
        String invalidDeadlineDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DeadlineDate(invalidDeadlineDate));
    }

    @Test
    public void isValidDeadlineDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> DeadlineDate.isValidDeadlineDate(null));

        // invalid date
        assertFalse(DeadlineDate.isValidDeadlineDate("")
                && DeadlineDate.isValidDeadlineDateInput("")); // empty string
        assertFalse(DeadlineDate.isValidDeadlineDate(" ")
                && DeadlineDate.isValidDeadlineDateInput(" ")); // spaces only
        assertFalse(DeadlineDate.isValidDeadlineDate("03121")
                && DeadlineDate.isValidDeadlineDateInput("03121")); // less than 6 numbers
        assertFalse(DeadlineDate.isValidDeadlineDate("phone")
                && DeadlineDate.isValidDeadlineDateInput("phone")); // non-numeric
        assertFalse(DeadlineDate.isValidDeadlineDate("9011p041")
                && DeadlineDate.isValidDeadlineDateInput("9011p041")); // alphabets within digits
        assertFalse(DeadlineDate.isValidDeadlineDate("9312 1534")
                && DeadlineDate.isValidDeadlineDateInput("9312 1534")); // spaces within digits
        assertFalse(DeadlineDate.isValidDeadlineDate("290219")
                && DeadlineDate.isValidDeadlineDateInput("290219")); // invalid date, date does not exist
        assertFalse(DeadlineDate.isValidDeadlineDate("2354226")
                && DeadlineDate.isValidDeadlineDateInput("2354226")); // more than 6 numbers

        // valid date
        assertTrue(DeadlineDate.isValidDeadlineDate("230319")
                && DeadlineDate.isValidDeadlineDateInput("230319")); // exactly 6 numbers
        assertTrue(DeadlineDate.isValidDeadlineDate("010119")
                && DeadlineDate.isValidDeadlineDateInput("010119")); // test lower boundary
        assertTrue(DeadlineDate.isValidDeadlineDate("311219")
                && DeadlineDate.isValidDeadlineDateInput("311219")); // test upper boundary
    }
}

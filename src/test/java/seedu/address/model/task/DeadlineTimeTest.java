package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DeadlineTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DeadlineTime(null));
    }

    @Test
    public void constructor_invalidDeadlineTime_throwsIllegalArgumentException() {
        String invalidDeadlineTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DeadlineTime(invalidDeadlineTime));
    }

    @Test
    public void isValidDeadlineTime() {
        // null time
        Assert.assertThrows(NullPointerException.class, () -> DeadlineTime.isValidDeadlineTime(null));

        // invalid time
        assertFalse(DeadlineTime.isValidDeadlineTime("")); // empty string
        assertFalse(DeadlineTime.isValidDeadlineTime(" ")); // spaces only
        assertFalse(DeadlineTime.isValidDeadlineTime("912")); // less than 4 numbers
        assertFalse(DeadlineTime.isValidDeadlineTime("phone")); // non-numeric
        assertFalse(DeadlineTime.isValidDeadlineTime("9011p041")); // alphabets within digits
        assertFalse(DeadlineTime.isValidDeadlineTime("9312 1534")); // spaces within digits
        assertFalse(DeadlineTime.isValidDeadlineTime("2400")); // invalid 24HR time format
        assertFalse(DeadlineTime.isValidDeadlineTime("23545")); // more than 4 numbers

        // valid time
        assertTrue(DeadlineTime.isValidDeadlineTime("1200")); // exactly 4 numbers
        assertTrue(DeadlineTime.isValidDeadlineTime("0000")); // test lower boundary
        assertTrue(DeadlineTime.isValidDeadlineTime("2359")); // test upper boundary
    }
}

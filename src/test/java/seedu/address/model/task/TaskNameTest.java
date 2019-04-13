package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TaskNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TaskName(null));
    }

    @Test
    public void constructor_invalidTaskName_throwsIllegalArgumentException() {
        String invalidTaskName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskName(invalidTaskName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> TaskName.isValidName(null));

        // invalid name
        assertFalse(TaskName.isValidName("")); // empty string
        assertFalse(TaskName.isValidName(" ")); // spaces only
        assertFalse(TaskName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(TaskName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(TaskName.isValidName("jerome tan")); // alphabets only
        assertTrue(TaskName.isValidName("3232")); // numbers only
        assertTrue(TaskName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(TaskName.isValidName("Capital Tan")); // with capital letters
        assertTrue(TaskName.isValidName("Steve American Jobs Apple Pseudo")); // long names
    }
}

package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EmployeeIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EmployeeId(null));
    }

    @Test
    public void constructor_invalidEmployeeId_throwsIllegalArgumentException() {
        String invalidEmployeeId = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Phone(invalidEmployeeId));
    }

    @Test
    public void isValidEmployeeId() {
        // null employee id
        Assert.assertThrows(NullPointerException.class, () -> EmployeeId.isValidEmployeeId(null));

        // invalid employee ids
        assertFalse(EmployeeId.isValidEmployeeId("")); // empty string
        assertFalse(EmployeeId.isValidEmployeeId(" ")); // spaces only
        assertFalse(EmployeeId.isValidEmployeeId("91")); // less than 6 numbers
        assertFalse(EmployeeId.isValidEmployeeId("phone")); // non-numeric
        assertFalse(EmployeeId.isValidEmployeeId("9011p041")); // alphabets within digits
        assertFalse(EmployeeId.isValidEmployeeId("9312 1534")); // spaces within digits

        // valid employee ids
        assertTrue(EmployeeId.isValidEmployeeId("911111")); // exactly 6 numbers
        assertTrue(EmployeeId.isValidEmployeeId("931212")); // exactly 6 numbers
    }
}


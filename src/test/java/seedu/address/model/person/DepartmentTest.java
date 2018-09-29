package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DepartmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Department(null));
    }

    @Test
    public void constructor_invalidDepartment_throwsIllegalArgumentException() {
        String invalidDepartment = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Department(invalidDepartment));
    }

    @Test
    public void isValidDepartment() {
        // null department name
        Assert.assertThrows(NullPointerException.class, () -> Department.isValidDepartment(null));

        // invalid department name
        assertFalse(Department.isValidDepartment("")); // empty string
        assertFalse(Department.isValidDepartment(" ")); // spaces only
        assertFalse(Department.isValidDepartment("^")); // only non-alphanumeric characters
        assertFalse(Department.isValidDepartment("junior*")); // contains non-alphanumeric characters

        // valid department name
        assertTrue(Department.isValidDepartment("junior management")); // alphabets only
        assertTrue(Department.isValidDepartment("12345")); // numbers only
        assertTrue(Department.isValidDepartment("1st junior")); // alphanumeric characters
        assertTrue(Department.isValidDepartment("Junior Management")); // with capital letters
        assertTrue(Department.isValidDepartment("2nd Junior Management")); // long names
    }
}

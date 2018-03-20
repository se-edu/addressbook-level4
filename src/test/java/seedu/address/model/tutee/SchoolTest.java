package seedu.address.model.tutee;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SchoolTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new School(null));
    }

    @Test
    public void constructor_invalidSchool_throwsIllegalArgumentException() {
        String invalidSchool = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new School(invalidSchool));
    }

    @Test
    public void isValidSchool() {
        // null school name
        Assert.assertThrows(NullPointerException.class, () -> School.isValidSchool(null));

        // invalid school name
        assertFalse(School.isValidSchool("")); // empty string
        assertFalse(School.isValidSchool(" ")); // spaces only
        assertFalse(School.isValidSchool("^")); // only non-alphabetic characters
        assertFalse(School.isValidSchool("bedok primary school*")); // contains non-alphabetic characters
        assertFalse(School.isValidSchool("911")); // numbers only
        assertFalse(School.isValidSchool("bedok12 secondary school")); // contains numbers

        // valid school name
        assertTrue(School.isValidSchool("victoria junior college")); // alphabets only
        assertTrue(School.isValidSchool("Victoria Junior College")); // with capital letters
        assertTrue(School.isValidSchool("The longest name school primary school")); // long subject name
    }

}

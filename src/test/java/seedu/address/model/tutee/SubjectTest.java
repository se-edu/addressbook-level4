package seedu.address.model.tutee;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author ChoChihTun
public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidSubject() {
        // null subject
        Assert.assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subject name
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only
        assertFalse(Subject.isValidSubject("^")); // only non-alphabetic characters
        assertFalse(Subject.isValidSubject("economics*")); // contains non-alphabetic characters
        assertFalse(Subject.isValidSubject("911")); // numbers only
        assertFalse(Subject.isValidSubject("math12")); // contains numbers

        // valid subject name
        assertTrue(Subject.isValidSubject("social studies")); // alphabets only
        assertTrue(Subject.isValidSubject("Social Studies")); // with capital letters
        assertTrue(Subject.isValidSubject("introduction to fluid dynamics")); // long subject name
    }

}

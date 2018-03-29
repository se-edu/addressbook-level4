package seedu.address.model.tutee;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author ChoChihTun
public class GradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructor_invalidGrade_throwsIllegalArgumentException() {
        String invalidGrade = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGrade));
    }

    @Test
    public void isValidGrade() {
        // null subject grade
        Assert.assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // invalid subject grade
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only
        assertFalse(Grade.isValidGrade("9112")); // only contains numbers
        assertFalse(Grade.isValidGrade("pass")); // more than 2 alphabet
        assertFalse(Grade.isValidGrade("+B")); // special character before alphabet
        assertFalse(Grade.isValidGrade("B -")); // spaces within digits

        // valid subject grade
        assertTrue(Grade.isValidGrade("A+")); // 1 alphabet followed by a special character
        assertTrue(Grade.isValidGrade("B")); // only 1 alphabet
        assertTrue(Grade.isValidGrade("b")); // small letter
        assertTrue(Grade.isValidGrade("C5")); // number after alphabet
    }

}

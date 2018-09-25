package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class GradeTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructorInvalidGradeThrowsIllegalArgumentException() {
        String invalidGrade = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGrade));
    }

    @Test
    public void isValidGrade() {
        // invalid grade format
        assertFalse(Grade.isValidGrade(" A+")); // no leading whitespace
        assertFalse(Grade.isValidGrade("A+ ")); // no leading whitespace
        assertFalse(Grade.isValidGrade("A +")); // no whitespace in between
        assertFalse(Grade.isValidGrade("G")); // First character has to be A, B, C, D, F

        // valid grade
        assertTrue(Grade.isValidGrade("A+"));
    }

    @Test
    public void toStringValid() {
        assertTrue(new Grade("A+").toString().contentEquals("A+"));
    }

    @Test
    public void equalsValid() {
        assertTrue(new Grade("A+").equals(new Grade("A+")));
    }
}

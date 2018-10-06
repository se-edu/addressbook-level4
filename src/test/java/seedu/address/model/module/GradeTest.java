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
        assertTrue(Grade.isValidGrade("A"));
        assertTrue(Grade.isValidGrade("A-"));
        assertTrue(Grade.isValidGrade("B+"));
        assertTrue(Grade.isValidGrade("B"));
        assertTrue(Grade.isValidGrade("B-"));
        assertTrue(Grade.isValidGrade("C+"));
        assertTrue(Grade.isValidGrade("C"));
        assertTrue(Grade.isValidGrade("D+"));
        assertTrue(Grade.isValidGrade("D"));
        assertTrue(Grade.isValidGrade("F"));
        assertTrue(Grade.isValidGrade("CU"));
        assertTrue(Grade.isValidGrade("CS"));
    }

    @Test
    public void affectCapValid() {
        assertTrue(new Grade("A+").affectsCap());
        assertTrue(new Grade("A").affectsCap());
        assertTrue(new Grade("A-").affectsCap());
        assertTrue(new Grade("B+").affectsCap());
        assertTrue(new Grade("B").affectsCap());
        assertTrue(new Grade("B-").affectsCap());
        assertTrue(new Grade("C+").affectsCap());
        assertTrue(new Grade("C").affectsCap());
        assertTrue(new Grade("D+").affectsCap());
        assertTrue(new Grade("D").affectsCap());
        assertTrue(new Grade("F").affectsCap());
        assertFalse(new Grade("CS").affectsCap());
        assertFalse(new Grade("CU").affectsCap());
    }

    @Test
    public void getPointValid() {
        assertTrue(new Grade("A+").getPoint() == 5);
        assertTrue(new Grade("A").getPoint() == 5);
        assertTrue(new Grade("A-").getPoint() == 4.5);
        assertTrue(new Grade("B+").getPoint() == 4.0);
        assertTrue(new Grade("B").getPoint() == 3.5);
        assertTrue(new Grade("B-").getPoint() == 3.0);
        assertTrue(new Grade("C+").getPoint() == 2.5);
        assertTrue(new Grade("C").getPoint() == 2);
        assertTrue(new Grade("D+").getPoint() == 1.5);
        assertTrue(new Grade("D").getPoint() == 1);
        assertTrue(new Grade("F").getPoint() == 0);
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

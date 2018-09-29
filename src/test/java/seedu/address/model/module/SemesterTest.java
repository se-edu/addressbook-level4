package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SemesterTest {

    @Test
    public void constructorInvalidYearThrowsIllegalArgumentException() {
        Assert.assertThrows(NullPointerException.class, () -> new Semester(null));
    }

    @Test
    public void isValidSemester() {
        // invalid semester format
        assertFalse(Semester.isValidSemester("s3"));
        assertFalse(Semester.isValidSemester("3"));

        // valid semester
        assertTrue(Semester.isValidSemester(Semester.SEMESTER_ONE));
        assertTrue(Semester.isValidSemester(Semester.SEMESTER_TWO));
        assertTrue(Semester.isValidSemester(Semester.SEMESTER_SPECIAL_ONE));
        assertTrue(Semester.isValidSemester(Semester.SEMESTER_SPECIAL_TWO));
    }

    @Test
    public void toStringValid() {
        assertTrue(new Semester(Semester.SEMESTER_ONE).toString().contentEquals(Semester.SEMESTER_ONE));
    }

    @Test
    public void equalsValid() {
        assertTrue(new Semester(Semester.SEMESTER_ONE).equals(new Semester(Semester.SEMESTER_ONE)));
    }
}

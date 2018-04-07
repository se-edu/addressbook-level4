package seedu.address.model.tutee;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author ChoChihTun
public class EducationLevelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EducationLevel(null));
    }

    @Test
    public void constructor_invalidEducationLevel_throwsIllegalArgumentException() {
        String invalidEducationLevel = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EducationLevel(invalidEducationLevel));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> EducationLevel.isValidEducationLevel(null));

        // invalid phone numbers
        assertFalse(EducationLevel.isValidEducationLevel("")); // empty string
        assertFalse(EducationLevel.isValidEducationLevel(" ")); // spaces only
        assertFalse(EducationLevel.isValidEducationLevel("91")); // numbers
        assertFalse(EducationLevel.isValidEducationLevel("university")); // not the specified education level
        assertFalse(EducationLevel.isValidEducationLevel("primary5")); // contains number
        assertFalse(EducationLevel.isValidEducationLevel("primary@")); // contains special characters

        // valid phone numbers
        assertTrue(EducationLevel.isValidEducationLevel("primary")); // primary school
        assertTrue(EducationLevel.isValidEducationLevel("secondary")); // secondary school
        assertTrue(EducationLevel.isValidEducationLevel("junior college")); // junior college
        assertTrue(EducationLevel.isValidEducationLevel("SeCoNdaRy")); // Capital
    }

}

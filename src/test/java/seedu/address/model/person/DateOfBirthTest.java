package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateOfBirthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_invalidDateOfBirth_throwsIllegalArgumentException() {
        String invalidDateOfBirth = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDateOfBirth));
    }

    @Test
    public void isValidDateOfBirth() {
        // null date of birth
        Assert.assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDateOfBirth(null));

        // invalid dates of birth
        assertFalse(DateOfBirth.isValidDateOfBirth("")); // empty string
        assertFalse(DateOfBirth.isValidDateOfBirth(" ")); // spaces only
        assertFalse(DateOfBirth.isValidDateOfBirth("91")); // numbers only, no forward slash
        assertFalse(DateOfBirth.isValidDateOfBirth("phone")); // non-numeric
        assertFalse(DateOfBirth.isValidDateOfBirth("9011p041")); // alphabets within digits
        assertFalse(DateOfBirth.isValidDateOfBirth("9312 1534")); // spaces within digits

        // valid dates of birth
        assertTrue(DateOfBirth.isValidDateOfBirth("12/06/1990")); // exactly 10 characters with 2 forward slash
        assertTrue(DateOfBirth.isValidDateOfBirth("31/02/1993")); // exactly 10 characters with 2 forward slash
    }
}

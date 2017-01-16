package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhoneTest {

    @Test
    public void isValidPhone_emptyString_returnsFalse() {
        assertFalse(Phone.isValidPhone(""));
    }

    @Test
    public void isValidPhone_spacesOnly_returnsFalse() {
        assertFalse(Phone.isValidPhone(" "));
    }

    @Test
    public void isValidPhone_nonNumeric_returnsFalse() {
        assertFalse(Phone.isValidPhone("phone"));
    }

    @Test
    public void isValidPhone_spacesInPhoneNumbers_returnsFalse() {
        assertFalse(Phone.isValidPhone("3912 1234"));
    }

    @Test
    public void isValidPhone_validPhoneNumber_returnsTrue() {
        assertTrue(Phone.isValidPhone("39121234"));
    }
}

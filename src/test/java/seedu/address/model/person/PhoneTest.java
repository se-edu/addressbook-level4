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
    public void isValidPhone_containsSpaces_returnsFalse() {
        assertFalse(Phone.isValidPhone(" "));
        assertFalse(Phone.isValidPhone("9312 1534"));
    }

    @Test
    public void isValidPhone_containsNonDigits_returnsFalse() {
        assertFalse(Phone.isValidPhone("phone"));
        assertFalse(Phone.isValidPhone("9011p041"));
    }

    @Test
    public void isValidPhone_digitsOnly_returnsTrue() {
        assertTrue(Phone.isValidPhone("93121534"));
    }
}

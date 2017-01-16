package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    @Test
    public void isValidName_emptyString_returnsFalse() {
        assertFalse(Name.isValidName(""));
    }

    @Test
    public void isValidName_spacesOnly_returnsFalse() {
        assertFalse(Name.isValidName(" "));
    }

    @Test
    public void isValidName_containsNonAlphanumericCharacters_returnsFalse() {
        assertFalse(Name.isValidName("^"));
        assertFalse(Name.isValidName("peter*"));
    }

    @Test
    public void isValidName_onlyAlphanumericCharacters_returnsTrue() {
        assertTrue(Name.isValidName("peter jack"));
        assertTrue(Name.isValidName("12345"));
        assertTrue(Name.isValidName("peter the 2nd"));
    }
}

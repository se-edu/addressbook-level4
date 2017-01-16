package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailTest {

    @Test
    public void isValidEmail_emptyString_returnsFalse() {
        assertFalse(Email.isValidEmail(""));
    }

    @Test
    public void isValidEmail_spacesOnly_returnsFalse() {
        assertFalse(Email.isValidEmail(" "));
    }

    @Test
    public void isValidEmail_noAddress_returnsFalse() {
        assertFalse(Email.isValidEmail("peterjack@"));
    }

    @Test
    public void isValidEmail_emailWithSpaces_returnsFalse() {
        assertFalse(Email.isValidEmail("peter jack@webmail.com"));
    }

    @Test
    public void isValidEmail_noAtSymbol_returnsFalse() {
        assertFalse(Email.isValidEmail("peterjackwebmail.com"));
    }

    @Test
    public void isValidEmail_noName_returnsFalse() {
        assertFalse(Email.isValidEmail("@webmail.com");
    }

    @Test
    public void isValidEmail_validEmail_returnsTrue() {
        assertTrue(Email.isValidEmail("peter.jack@webmail.com"));
    }

}

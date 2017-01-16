package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailTest {

    @Test
    public void isValidEmail_emptyString_returnsFalse() {
        assertFalse(Email.isValidEmail(""));    // empty string
        assertFalse(Email.isValidEmail(" "));   // spaces only
    }

    @Test
    public void isValidEmail_missingParts_returnsFalse() {
        assertFalse(Email.isValidEmail("@webmail.com"));            // missing local part
        assertFalse(Email.isValidEmail("peterjackwebmail.com"));    // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@"));              // missing domain part
    }

    @Test
    public void isValidEmail_invalidParts_returnsFalse() {
        assertFalse(Email.isValidEmail("-@webmail.com"));
        assertFalse(Email.isValidEmail("peterjack@-"));
        assertFalse(Email.isValidEmail("peter jack@webmail.com"));
        assertFalse(Email.isValidEmail("peterjack@web mail.com"));
        assertFalse(Email.isValidEmail("peterjack@@webmail.com"));  // double '@' symbol
    }

    @Test
    public void isValidEmail_validEmail_returnsTrue() {
        assertTrue(Email.isValidEmail("peter.jack@webmail.com"));
    }
}

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
        assertFalse(Email.isValidEmail("peterjackwebmail.com"));    // missing at symbol
        assertFalse(Email.isValidEmail("peterjack@"));              // missing domain part
    }

    @Test
    public void isValidEmail_invalidParts_returnsFalse() {
        // invalid characters
        assertFalse(Email.isValidEmail("-@webmail.com"));
        assertFalse(Email.isValidEmail("peterjack@-"));

        // spaces
        assertFalse(Email.isValidEmail("peter jack@webmail.com"));
        assertFalse(Email.isValidEmail("peterjack@web mail.com"));
    }

    @Test
    public void isValidEmail_validEmail_returnsTrue() {
        assertTrue(Email.isValidEmail("peter.jack@webmail.com"));
    }
}

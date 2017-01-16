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
    public void isValidEmail_missingLocalPart_returnsFalse() {
        assertFalse(Email.isValidEmail("@webmail.com"));
    }

    @Test
    public void isValidEmail_missingAtSymbol_returnsFalse() {
        assertFalse(Email.isValidEmail("peterjackwebmail.com"));
    }

    @Test
    public void isValidEmail_missingDomain_returnsFalse() {
        assertFalse(Email.isValidEmail("peterjack@"));
    }

    @Test
    public void isValidEmail_invalidLocalPart_returnsFalse() {
        assertFalse(Email.isValidEmail("-@webmail.com"));
        assertFalse(Email.isValidEmail("peter jack@webmail.com"));
    }

    @Test
    public void isValidEmail_invalidDomain_returnsFalse() {
        assertFalse(Email.isValidEmail("peterjack@-"));
        assertFalse(Email.isValidEmail("peterjack@web mail.com"));
    }

    @Test
    public void isValidEmail_validEmailAddress_returnsTrue() {
        assertTrue(Email.isValidEmail("peter.jack@webmail.com"));
    }
}

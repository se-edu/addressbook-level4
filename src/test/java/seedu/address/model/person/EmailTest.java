package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@webmail.com")); // missing local part
        assertFalse(Email.isValidEmail("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain part

        // invalid parts
        assertFalse(Email.isValidEmail("-@webmail.com")); // invalid local part
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain part
        assertFalse(Email.isValidEmail("peter jack@webmail.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@web mail.com")); // spaces in domain part
        assertFalse(Email.isValidEmail("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("peterjack@webmail@com")); // '@' symbol in domain part

        // valid email
        assertTrue(Email.isValidEmail("peter.jack@webmail.com"));
        assertTrue(Email.isValidEmail("Peter.Jack@WEBmail.com")); // mixed-case email address
    }
}

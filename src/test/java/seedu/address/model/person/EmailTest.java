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
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("-@webmail.com")); // invalid local part
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmail("peter jack@webmail.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@web mail.com")); // spaces in domain name
        assertFalse(Email.isValidEmail("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Email.isValidEmail("a@b"));  // minimal
        assertTrue(Email.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(Email.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(Email.isValidEmail("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Email.isValidEmail("_user_@_do_main_.com_"));    // underscores
        assertTrue(Email.isValidEmail("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}

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
        assertFalse(Email.isValidEmail("@example.com")); // missing local part
        assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("-@example.com")); // invalid local part
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Email.isValidEmail("a@b"));  // minimal
        assertTrue(Email.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(Email.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(Email.isValidEmail("a1@example1.com"));  // mixture of alphanumeric and dot characters
        assertTrue(Email.isValidEmail("_user_@_e_x_a_m_p_l_e_.com_"));    // underscores
        assertTrue(Email.isValidEmail("peter_jack@very_very_very_long_example.com"));   // long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com"));    // long local part
    }
}

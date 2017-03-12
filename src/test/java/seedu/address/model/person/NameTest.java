package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("Adam \r Jackson")); // contains carriage-return character
        assertFalse(Name.isValidName("\n Adam Jackson")); // contains newline character
        assertFalse(Name.isValidName("Adam Jackson \f")); // contains form-feed character
        assertFalse(Name.isValidName("Adam \u000BJackson")); // contains vertical tab character
        assertFalse(Name.isValidName("Adam\u0085 Jackson")); // contains next line character
        assertFalse(Name.isValidName("\u2028 Adam Jackson")); // contains line separator character
        assertFalse(Name.isValidName("\u2029 Adam Jackson")); // contains paragraph separator character

        // valid name
        assertTrue(Name.isValidName(" Peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("'t Hart, Jolie-Pitt, Jr. 3 ・^")); // contains punctuation marks
        // unicode format based on http://stackoverflow.com/questions/4237581/comparing-unicode-characters-in-junit
        assertTrue(Name.isValidName("Adam \u00E9-\u0041 \u030A")); // contains unicode letters and marks
        assertTrue(Name.isValidName("\u2660 \u21B7")); // non-letter unicode characters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}

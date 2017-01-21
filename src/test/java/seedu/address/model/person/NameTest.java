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
        assertFalse(Name.isValidName(" peter the 2nd")); // starting with space

        // valid name
        assertTrue(Name.isValidName("Peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("'t Hart, Jolie-Pitt, Jr. 3 ・^")); // contains punctuation marks
        // unicode format based on http://stackoverflow.com/questions/4237581/comparing-unicode-characters-in-junit
        assertTrue(Name.isValidName("Adam \u00E9-\u0041 \u030A")); // contains unicode letters and marks
        assertTrue(Name.isValidName("\u2660 \u21B7")); // non-letter unicode characters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}

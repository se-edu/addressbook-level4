package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {
    @Test
    public void isValidName() {
        // invalid names
        assertIsInvalidName(null);
        assertIsInvalidName("");
        assertIsInvalidName("  "); // spaces
        assertIsInvalidName("\u0009"); // tab
        assertIsInvalidName("\n "); // contains Java whitespace character
        assertIsInvalidName(" \u000B"); // contains Unicode space (vertical tab)

        // valid names
        assertIsValidName(" Peter the 2nd"); // alphanumeric characters
        assertIsValidName("'t Hart, Jolie-Pitt, Jr. 3 ・^"); // contains punctuation marks
        // unicode format based on http://stackoverflow.com/questions/4237581/comparing-unicode-characters-in-junit
        assertIsValidName("Adam \u00E9-\u0041 \u030A"); // contains Unicode letters and marks
        assertIsValidName("\u2660 \u21B7"); // non-letter Unicode characters
        assertIsValidName("David Roger Jackson Ray Jr 2nd"); // long names
    }

    private void assertIsInvalidName(String name) {
        assertFalse(Name.isValidName(name));
    }

    private void assertIsValidName(String name) {
        assertTrue(Name.isValidName(name));
    }
}

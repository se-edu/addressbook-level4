package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CodeTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Code(null));
    }

    @Test
    public void constructorInvalidCodeThrowsIllegalArgumentException() {
        String invalidCode = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Code(invalidCode));
    }

    @Test
    public void isValidCode() {
        // invalid code format
        assertFalse(Code.isValidCode("")); // cannot be blank
        assertFalse(Code.isValidCode(" CS2103")); // no leading whitespace
        assertFalse(Code.isValidCode("CS2103 ")); // no leading whitespace
        assertFalse(Code.isValidCode("CS 2103")); // no whitespace in between

        // valid code
        assertTrue(Code.isValidCode("CS2103")); // no whitespace
    }
}

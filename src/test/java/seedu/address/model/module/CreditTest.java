package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CreditTest {
    @Test
    public void constructor_invalidCredit_throwsIllegalArgumentException() {
        int invalidCredit = 0;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Credit(invalidCredit));
    }

    @Test
    public void isValidCredit() {
        // invalid code format
        assertFalse(Credit.isValidCredit(0)); // must be greater than or equal to 1
        assertFalse(Credit.isValidCredit(21)); // must be lower than or equal to 20

        // valid code
        assertTrue(Credit.isValidCredit(4)); // credit between 1 and 20
    }
}

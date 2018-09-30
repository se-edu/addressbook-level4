package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class BonusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Bonus(null));
    }

    @Test
    public void constructor_invalidBonus_throwsIllegalArgumentException() {
        String invalidBonus = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Bonus(invalidBonus));
    }

    @Test
    public void isValidBonus() {
        // null bonus
        Assert.assertThrows(NullPointerException.class, () -> Bonus.isValidBonus(null));

        // invalid bonuses
        assertFalse(Bonus.isValidBonus("")); // empty string
        assertFalse(Bonus.isValidBonus(" ")); // spaces only
        assertFalse(Bonus.isValidBonus("salary")); // non-numeric
        assertFalse(Bonus.isValidBonus("9011p041")); // alphabets within digits
        assertFalse(Bonus.isValidBonus("9312 1534")); // spaces within digits

        // valid bonuses
        assertTrue(Bonus.isValidBonus("10.33")); // numbers and a dot
        assertTrue(Bonus.isValidBonus("9312.1534")); // numbers and a dot
    }
}

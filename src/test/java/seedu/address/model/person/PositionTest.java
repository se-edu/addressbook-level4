package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PositionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Position(null));
    }

    @Test
    public void constructor_invalidPosition_throwsIllegalArgumentException() {
        String invalidPosition = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Position(invalidPosition));
    }

    @Test
    public void isValidPosition() {
        // null position
        Assert.assertThrows(NullPointerException.class, () -> Position.isValidPosition(null));

        // invalid positions
        assertFalse(Position.isValidPosition("")); // empty string
        assertFalse(Position.isValidPosition(" ")); // // less than 2 characters
        assertFalse(Position.isValidPosition("91")); // only numeric characters
        assertFalse(Position.isValidPosition("Finance123")); // contains numeric characters

        // valid positions
        assertTrue(Position.isValidPosition("Manager")); // alphabets only
        assertTrue(Position.isValidPosition("HR Admin")); // alphabets and space
    }
}

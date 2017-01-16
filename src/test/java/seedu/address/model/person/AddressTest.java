package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddressTest {

    @Test
    public void isValidAddress_emptyString_returnsFalse() {
        assertFalse(Address.isValidAddress(""));
    }

    @Test
    public void isValidAddress_spacesOnly_returnsFalse() {
        assertFalse(Address.isValidAddress(" "));
    }

    @Test
    public void isValidAddress_nonEmptyString_returnsTrue() {
        assertTrue(Address.isValidAddress("Block 335"));
    }

    @Test
    public void isValidAddress_singleCharacterString_returnsTrue() {
        assertTrue(Address.isValidAddress("-"));
    }

}

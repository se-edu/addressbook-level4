package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IndexUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void oneToZeroIndex_validInput_success() {
        assertEquals(0, IndexUtil.oneToZeroIndex(1));
        assertEquals(7, IndexUtil.oneToZeroIndex(8));
    }

    @Test
    public void oneToZeroIndex_zeroGiven_exceptionThrown() throws IndexOutOfBoundsException {
        thrown.expect(IndexOutOfBoundsException.class);
        IndexUtil.oneToZeroIndex(0);
    }

    @Test
    public void oneToZeroIndex_negativeValueGiven_exceptionThrown() throws IndexOutOfBoundsException {
        thrown.expect(IndexOutOfBoundsException.class);
        IndexUtil.oneToZeroIndex(-1);
    }
}

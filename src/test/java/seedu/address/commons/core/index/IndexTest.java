package seedu.address.commons.core.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class IndexTest {

    @Test
    public void createOneBasedIndex() {
        // invalid index
        assertCreateOneBasedFailure(0);

        // check equality using the same base
        assertEquals(1, Index.createFromOneBased(1).getOneBased());
        assertEquals(5, Index.createFromOneBased(5).getOneBased());

        // convert from one-based index to zero-based index
        assertEquals(0, Index.createFromOneBased(1).getZeroBased());
        assertEquals(4, Index.createFromOneBased(5).getZeroBased());
    }

    @Test
    public void createZeroBasedIndex() {
        // invalid index
        assertCreateZeroBasedFailure(-1);

        // check equality using the same base
        assertEquals(0, Index.createFromZeroBased(0).getZeroBased());
        assertEquals(5, Index.createFromZeroBased(5).getZeroBased());

        // convert from zero-based index to one-based index
        assertEquals(1, Index.createFromZeroBased(0).getOneBased());
        assertEquals(6, Index.createFromZeroBased(5).getOneBased());
    }


    /**
     * Executes {@code Index#createFromOneBased(int)} with {@code invalidOneBasedIndex}, confirms that an
     * {@code IndexOutOfBoundsException} is thrown.
     */
    private void assertCreateOneBasedFailure(int invalidOneBasedIndex) {
        try {
            Index.createFromOneBased(invalidOneBasedIndex);
            fail("Expected IndexOutOfBoundsException to be thrown.");
        } catch (IndexOutOfBoundsException ie) {
            // expected behaviour
        }
    }


    /**
     * Executes {@code Index#createFromZeroBased(int)} with {@code invalidZeroBasedIndex}, confirms that an
     * {@code IndexOutOfBoundsException} is thrown.
     */
    private void assertCreateZeroBasedFailure(int invalidZeroBasedIndex) {
        try {
            Index.createFromZeroBased(invalidZeroBasedIndex);
            fail("Expected IndexOutOfBoundsException to be thrown.");
        } catch (IndexOutOfBoundsException ie) {
            // expected behaviour
        }
    }

    @Test
    public void equals() {
        final Index fifthPersonIndex = Index.createFromOneBased(5);

        // same values -> returns true
        assertTrue(fifthPersonIndex.equals(Index.createFromOneBased(5)));
        assertTrue(fifthPersonIndex.equals(Index.createFromZeroBased(4)));

        // same object -> returns true
        assertTrue(fifthPersonIndex.equals(fifthPersonIndex));

        // null -> returns false
        assertFalse(fifthPersonIndex.equals(null));

        // different types -> returns false
        assertFalse(fifthPersonIndex.equals(5.0f));

        // different index -> returns false
        assertFalse(fifthPersonIndex.equals(Index.createFromOneBased(1)));
    }
}

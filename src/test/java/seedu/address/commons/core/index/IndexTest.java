package seedu.address.commons.core.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IndexTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createOneBasedIndex() {
        // check equality for the same base
        assertEquals(1, Index.createFromOneBased(1).getOneBased());
        assertEquals(5, Index.createFromOneBased(5).getOneBased());

        // convert to zero-based index
        assertEquals(0, Index.createFromOneBased(1).getZeroBased());
        assertEquals(4, Index.createFromOneBased(5).getZeroBased());
    }

    @Test
    public void createOneBasedIndex_invalidIndex_throwsOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);

        Index.createFromOneBased(0);
    }

    @Test
    public void createZeroBasedIndex() {
        // check equality for the same base
        assertEquals(0, Index.createFromZeroBased(0).getZeroBased());
        assertEquals(5, Index.createFromZeroBased(5).getZeroBased());

        // convert to one-based index
        assertEquals(1, Index.createFromZeroBased(0).getOneBased());
        assertEquals(6, Index.createFromZeroBased(5).getOneBased());
    }

    @Test
    public void createZeroBasedIndex_invalidIndex_throwsOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);

        Index.createFromZeroBased(-1);
    }

    @Test
    public void equals() {
        final Index fifthPersonIndex = Index.createFromOneBased(5);

        // same values -> returns true
        Index fifthPersonIndexCopy = Index.createFromOneBased(5);
        assertTrue(fifthPersonIndex.equals(fifthPersonIndexCopy));

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

package seedu.address.commons.core.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IndexTest {
    @Test
    public void createOneBasedIndex() {
        // get back the same index type
        assertEquals(1, Index.createFromOneBased(1).getOneBasedIndex());
        assertEquals(5, Index.createFromOneBased(5).getOneBasedIndex());

        // convert to zero-based index
        assertEquals(0, Index.createFromOneBased(1).getZeroBasedIndex());
        assertEquals(4, Index.createFromOneBased(5).getZeroBasedIndex());
    }

    @Test
    public void createZeroBasedIndex() {
        // get back the same index type
        assertEquals(0, Index.createFromZeroBased(0).getZeroBasedIndex());
        assertEquals(5, Index.createFromZeroBased(5).getZeroBasedIndex());

        // convert to one-based index
        assertEquals(1, Index.createFromZeroBased(0).getOneBasedIndex());
        assertEquals(6, Index.createFromZeroBased(5).getOneBasedIndex());
    }

    @Test
    public void equals() {
        final Index indexFifthPerson = Index.createFromOneBased(5);

        // same values -> returns true
        Index indexWithSameValues = Index.createFromOneBased(5);
        assertTrue(indexFifthPerson.equals(indexWithSameValues));

        // same object -> returns true
        assertTrue(indexFifthPerson.equals(indexFifthPerson));

        // null -> returns false
        assertFalse(indexFifthPerson.equals(null));

        // different types -> returns false
        assertFalse(indexFifthPerson.equals(""));

        // different index -> returns false
        assertFalse(indexFifthPerson.equals(Index.createFromOneBased(1)));
    }
}

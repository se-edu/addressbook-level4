package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TestUtil.toSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IndexUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void oneToZeroIndexSet_nullReference_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IndexUtil.oneToZeroIndex(null);
    }

    @Test
    public void oneToZeroIndexSet_containingNull_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IndexUtil.oneToZeroIndex(toSet((Integer) null));
    }

    @Test
    public void oneToZeroIndexSet_negativeIndices_assertionError() throws Exception {
        thrown.expect(AssertionError.class);
        IndexUtil.oneToZeroIndex(toSet(-1));
    }

    @Test
    public void oneToZeroIndexSet_zeroValuedIndices_assertionError() throws Exception {
        thrown.expect(AssertionError.class);
        IndexUtil.oneToZeroIndex(toSet(0));
    }

    @Test
    public void oneToZeroIndexSet_emptySet_emptyResult() throws Exception {
        assertOneToZeroIndexSetSuccess(Collections.emptySet(), Collections.emptySet());
    }

    @Test
    public void oneToZeroIndexSet_validIndices_correctResult() throws Exception {
        assertOneToZeroIndexSetSuccess(toSet(1, 5, 6), toSet(0, 4, 5));
        assertOneToZeroIndexSetSuccess(toSet(1, 1, 1), toSet(0, 0, 0)); // duplicate indices
    }

    private void assertOneToZeroIndexSetSuccess(Set<Integer> testIntegers, Set<Integer> expectedIntegers) {
        assertEquals(expectedIntegers, IndexUtil.oneToZeroIndex(testIntegers));
    }

    @Test
    public void oneToZeroIndexInt_negativeIndex_assertionError() throws Exception {
        thrown.expect(AssertionError.class);
        IndexUtil.oneToZeroIndex(-1);
    }

    @Test
    public void oneToZeroIndexInt_zeroValuedIndex_assertionError() throws Exception {
        thrown.expect(AssertionError.class);
        IndexUtil.oneToZeroIndex(0);
    }

    @Test
    public void oneToZeroIndexInt_validIndex_correctResult() throws Exception {
        assertOneToZeroIndexIntSuccess(1, 0);
        assertOneToZeroIndexIntSuccess(5, 4);
        assertOneToZeroIndexIntSuccess(10, 9);
    }

    private void assertOneToZeroIndexIntSuccess(int testInteger, int expectedInteger) {
        assertEquals(expectedInteger, IndexUtil.oneToZeroIndex(testInteger));
    }

    @Test
    public void areIndicesWithinBoundsStartEndRange_nullIndicesReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        IndexUtil.areIndicesWithinBounds(null, 0, 1);
    }

    @Test
    public void areIndicesWithinBoundsStartEndRange_nullIndicesElements_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        IndexUtil.areIndicesWithinBounds(Arrays.asList(null, null, null), 0, 1);
    }

    @Test
    public void areIndicesWithinBoundsStartEndRange_negativeStart_assertionError() {
        thrown.expect(AssertionError.class);
        IndexUtil.areIndicesWithinBounds(Arrays.asList(0), -1, 2);
    }

    @Test
    public void areIndicesWithinBoundsStartEndRange_startMoreThanEnd_assertionError() {
        thrown.expect(AssertionError.class);
        IndexUtil.areIndicesWithinBounds(Arrays.asList(2), 3, 2);
    }

    @Test
    public void areIndicesWithinBoundsStartEndRange_emptyIndicesCollection_correctResult() {
        assertTrue(IndexUtil.areIndicesWithinBounds(Collections.emptyList(), 0, 1));
    }

    @Test
    public void areIndicesWithinBoundsStartEndRange_startEqualsEnd_correctResult() {
        assertFalse(IndexUtil.areIndicesWithinBounds(Arrays.asList(0), 0, 0));
        assertFalse(IndexUtil.areIndicesWithinBounds(Arrays.asList(1), 0, 0));
    }

    @Test
    public void areIndicesWithinBoundsStartEndRange_validInputs_correctResult() {
        // invalid indices at the front, middle and rear
        assertFalse(IndexUtil.areIndicesWithinBounds(Arrays.asList(1, 3, 5), 2, 6));
        assertFalse(IndexUtil.areIndicesWithinBounds(Arrays.asList(3, 6, 5), 2, 6));
        assertFalse(IndexUtil.areIndicesWithinBounds(Arrays.asList(2, 3, 1), 2, 6));

        // all indices within bounds
        assertTrue(IndexUtil.areIndicesWithinBounds(Arrays.asList(1, 2, 3, 4), 1, 5));
        assertTrue(IndexUtil.areIndicesWithinBounds(Arrays.asList(1, 1, 1, 1), 1, 5)); // duplicate indices
        assertTrue(IndexUtil.areIndicesWithinBounds(Arrays.asList(4), 1, 5)); // list containing just a single index
    }
}

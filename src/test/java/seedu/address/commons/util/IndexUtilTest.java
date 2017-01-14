package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IndexUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private void assertOneToZeroIndexSetSuccess(Set<Integer> testIntegers,
                                                Set<Integer> expectedIntegers) {
        assertEquals(expectedIntegers, IndexUtil.oneToZeroIndex(testIntegers));
    }

    private void assertOneToZeroIndexIntSuccess(int testInteger, int expectedInteger) {
        assertEquals(expectedInteger, IndexUtil.oneToZeroIndex(testInteger));
    }

    @Test
    public void oneToZeroIndexSet_nullReference_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IndexUtil.oneToZeroIndex(null);
    }

    @Test
    public void oneToZeroIndexSet_containingNull_throwsNullPointerException() throws Exception {
        Set<Integer> containingNull = new HashSet<>();
        containingNull.add(null);
        thrown.expect(NullPointerException.class);
        IndexUtil.oneToZeroIndex(containingNull);
    }

    @Test
    public void oneToZeroIndexSet_negativeElements_assertionError() throws Exception {
        thrown.expect(AssertionError.class);
        IndexUtil.oneToZeroIndex(new HashSet<>(Arrays.asList(-10, -5, -2)));
    }

    @Test
    public void oneToZeroIndexSet_zeroElements_assertionError() throws Exception {
        thrown.expect(AssertionError.class);
        IndexUtil.oneToZeroIndex(new HashSet<>(Arrays.asList(0, 0, 0)));
    }

    @Test
    public void oneToZeroIndexSet_emptySet_emptyResult() throws Exception {
        assertOneToZeroIndexSetSuccess(Collections.emptySet(), Collections.emptySet());
    }

    @Test
    public void oneToZeroIndexSet_validElements_correctResult() throws Exception {
        // one based indices i.e. indices > 0
        assertOneToZeroIndexSetSuccess(new HashSet<>(Arrays.asList(1, 5, 6)), new HashSet<>(Arrays.asList(0, 4, 5)));
        assertOneToZeroIndexSetSuccess(new HashSet<>(Arrays.asList(10, 4, 3)), new HashSet<>(Arrays.asList(9, 3, 2)));
        assertOneToZeroIndexSetSuccess(new HashSet<>(Arrays.asList(1, 1, 1)), new HashSet<>(Arrays.asList(0, 0, 0)));
    }

    @Test
    public void oneToZeroIndexInt_negativeIndex_assertionError() throws Exception {
        thrown.expect(AssertionError.class);
        IndexUtil.oneToZeroIndex(-1);
    }

    @Test
    public void oneToZeroIndexInt_zeroIndex_assertionError() throws Exception {
        thrown.expect(AssertionError.class);
        IndexUtil.oneToZeroIndex(0);
    }

    @Test
    public void oneToZeroIndexInt_validIndex_correctResult() throws Exception {
        // one based indices i.e. indices > 0
        assertOneToZeroIndexIntSuccess(1, 0);
        assertOneToZeroIndexIntSuccess(5, 4);
        assertOneToZeroIndexIntSuccess(10, 9);
    }
}

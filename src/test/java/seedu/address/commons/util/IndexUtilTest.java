package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IndexUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void oneToZeroIndex_nullReference_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IndexUtil.oneToZeroIndex(null);
    }

    @Test
    public void oneIndexToZeroIndex_containingNull_throwsNullPointerException() throws Exception {
        Collection<Integer> containingNull = new ArrayList<>();
        containingNull.add(null);
        thrown.expect(NullPointerException.class);
        IndexUtil.oneToZeroIndex(containingNull);
    }

    @Test
    public void oneToZeroIndex_emptyCollection_emptyResult() throws Exception {
        assertOneToZeroIndexSuccess(new Integer[0], new Integer[0]);
    }

    @Test
    public void oneToZeroIndex_validElements_correctResult() throws Exception {
        // positive elements
        assertOneToZeroIndexSuccess(new Integer[] { 1, 5, 6 }, new Integer[] { 0, 4, 5 });
        assertOneToZeroIndexSuccess(new Integer[] { 10, 4, 3 }, new Integer[] { 9, 3, 2 });

        // negative elements
        assertOneToZeroIndexSuccess(new Integer[] { -10, -5, -2 }, new Integer[] { -11, -6, -3 });
        assertOneToZeroIndexSuccess(new Integer[] { -3, -8, -1 }, new Integer[] { -4, -9, -2 });

        // 0 elements
        assertOneToZeroIndexSuccess(new Integer[] { 0, 0, 0 }, new Integer[] { -1, -1, -1 });
    }

    private void assertOneToZeroIndexSuccess(Integer[] testIntegers, Integer[] expectedIntegers) {
        assertEquals(Arrays.asList(expectedIntegers), IndexUtil.oneToZeroIndex(Arrays.asList(testIntegers)));
    }
}

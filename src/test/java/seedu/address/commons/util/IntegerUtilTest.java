package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IntegerUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void applyOffset_nullReference_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IntegerUtil.applyOffset(null, 1);
    }

    @Test
    public void applyOffset_containingNull_throwsNullPointerException() throws Exception {
        Collection<Integer> containingNull = new ArrayList<>();
        containingNull.add(null);
        thrown.expect(NullPointerException.class);
        IntegerUtil.applyOffset(containingNull, 1);
    }

    @Test
    public void applyOffset_emptyCollection_emptyResult() throws Exception {
        assertApplyOffsetSuccess(1, new Integer[0], new Integer[0]);
    }

    @Test
    public void applyOffset_validOffset_correctResult() throws Exception {
        // positive offset
        assertApplyOffsetSuccess(2, new Integer[] { 1, 5, 0 }, new Integer[] { 3, 7, 2 });
        assertApplyOffsetSuccess(5, new Integer[] { -1, 4, 3 }, new Integer[] { 4, 9, 8 });

        // negative offset
        assertApplyOffsetSuccess(-3, new Integer[] { -10, 0, 2 }, new Integer[] { -13, -3, -1 });
        assertApplyOffsetSuccess(-1, new Integer[] { 0, 0, 0 }, new Integer[] { -1, -1, -1 });

        // zero offset
        assertApplyOffsetSuccess(0, new Integer[] { -1, -6, -8 }, new Integer[] { -1, -6, -8 });
        assertApplyOffsetSuccess(0, new Integer[] { 1, 0, -1 }, new Integer[] { 1, 0, -1 });
    }

    private void assertApplyOffsetSuccess(int offset, Integer[] test, Integer[] expectedIntegers) {
        Collection<Integer> testIntegers = new ArrayList<>(Arrays.asList(test));
        IntegerUtil.applyOffset(testIntegers, offset);
        assertEquals(Arrays.asList(expectedIntegers), testIntegers);
    }
}

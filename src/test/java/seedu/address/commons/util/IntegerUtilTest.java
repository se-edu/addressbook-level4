package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IntegerUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void applyOffset_nullReference_assertionError() throws Exception {
        thrown.expect(AssertionError.class);
        IntegerUtil.applyOffset(null, 1);
    }

    @Test
    public void applyOffset_containingNull_assertionError() throws Exception {
        Collection<Integer> containingNull = new ArrayList<>();
        containingNull.add(null);
        thrown.expect(AssertionError.class);
        IntegerUtil.applyOffset(containingNull, 1);
    }

    @Test
    public void applyOffset_emptyCollection_emptyResult() throws Exception {
        Collection<Integer> emptyList = Collections.emptyList();
        IntegerUtil.applyOffset(emptyList, 1);
        assertEquals(Collections.emptyList(), emptyList);
    }

    @Test
    public void applyOffset_validOffset_correctResult() throws Exception {
        // positive offset
        assertApplyOffsetSuccess(2, 1, 5, 0);
        assertApplyOffsetSuccess(5, -1, 4, 3);

        // negative offset
        assertApplyOffsetSuccess(-3, -10, 0, 2);
        assertApplyOffsetSuccess(-1, 0, 0, 0);

        // zero offset
        assertApplyOffsetSuccess(0, -1, -6, -8);
        assertApplyOffsetSuccess(0, 1, 0 , -1);
    }

    private void assertApplyOffsetSuccess(int offset, Integer... integers) {
        Collection<Integer> expectedIntegers = Stream.of(integers).map(i -> i + offset).collect(Collectors.toList());
        Collection<Integer> testIntegers = new ArrayList<>(Arrays.asList(integers));
        IntegerUtil.applyOffset(testIntegers, offset);
        assertEquals(expectedIntegers, testIntegers);
    }
}

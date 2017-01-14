package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TestUtil.toSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ListUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void subList_nullListReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(null, toSet(0));
    }

    @Test
    public void subList_nullIndicesSetReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(Arrays.asList(1, "spam"), (Set<Integer>) null);
    }

    @Test
    public void subList_nullIndicesSetElements_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(Arrays.asList("eggs"), toSet((Integer) null));
    }

    @Test
    public void subList_nonEmptyListOutOfBoundsIndices_assertionError() {
        List<Object> list = Arrays.asList("spam", 6, new Object());
        thrown.expect(AssertionError.class);
        ListUtil.subList(list, toSet(list.size()));
    }

    @Test
    public void subList_emptyListOutOfBoundsIndices_assertionError() {
        thrown.expect(AssertionError.class);
        ListUtil.subList(Collections.emptyList(), toSet(0, 3, 6));
    }

    @Test
    public void subList_emptyIndicesSet_emptyResult() {
        assertEquals(Collections.emptyList(), ListUtil.subList(Arrays.asList("eggs"), Collections.emptySet()));
    }

    @Test
    public void subList_validInputs_correctResult() {
        // list containing nulls
        List<Object> list = Arrays.asList(null, "spam", 5);
        List<Object> expectedSubList = Arrays.asList((Object) null);
        assertEquals(expectedSubList, ListUtil.subList(list, toSet(0)));

        final int max = list.size() - 1;
        final int mid = max / 2;
        final int min = 0;

        list = Arrays.asList("spam", 5, "eggs", "ham", new Object());
        expectedSubList = Arrays.asList(list.get(min), list.get(mid), list.get(max));

        // indices in sorted order
        assertEquals(expectedSubList, ListUtil.subList(list, toSet(min, mid, max)));

        // indices unsorted but original list order maintained
        assertEquals(expectedSubList, ListUtil.subList(list, toSet(max, mid, min)));
        assertEquals(expectedSubList, ListUtil.subList(list, toSet(max, min, mid)));
        assertEquals(expectedSubList, ListUtil.subList(list, toSet(mid, max, min)));
        assertEquals(expectedSubList, ListUtil.subList(list, toSet(mid, min, max)));
        assertEquals(expectedSubList, ListUtil.subList(list, toSet(min, max, mid)));
    }

}

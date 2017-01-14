package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TestUtil.asIntegerSet;

import java.util.ArrayList;
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
        ListUtil.subList(null, asIntegerSet(0));
    }

    @Test
    public void subList_nullIndicesSetReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(Arrays.asList(1, "spam"), (Set<Integer>) null);
    }

    @Test
    public void subList_nullIndicesSetElements_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(Arrays.asList("eggs"), asIntegerSet((Integer) null));
    }

    @Test
    public void subList_nonEmptyListOutOfBoundsIndices_assertionError() {
        List<Object> list = Arrays.asList("spam", 6, new ArrayList<>());
        thrown.expect(AssertionError.class);
        ListUtil.subList(list, asIntegerSet(list.size()));
    }

    @Test
    public void subList_emptyListOutOfBoundsIndices_assertionError() {
        thrown.expect(AssertionError.class);
        ListUtil.subList(Collections.emptyList(), asIntegerSet(0, 3, 6));
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
        assertEquals(expectedSubList, ListUtil.subList(list, asIntegerSet(0)));

        list = Arrays.asList("spam", 5, "eggs", "ham", new ArrayList<>());
        expectedSubList = Arrays.asList("spam", "eggs", new ArrayList<>());

        // indices in sorted order
        assertEquals(expectedSubList, ListUtil.subList(list, asIntegerSet(0, 2, list.size() - 1)));

        // indices unsorted but original list order maintained
        assertEquals(expectedSubList, ListUtil.subList(list, asIntegerSet(list.size() - 1, 2, 0)));
        assertEquals(expectedSubList, ListUtil.subList(list, asIntegerSet(list.size() - 1, 0, 2)));
        assertEquals(expectedSubList, ListUtil.subList(list, asIntegerSet(2, list.size() - 1, 0)));
        assertEquals(expectedSubList, ListUtil.subList(list, asIntegerSet(2, 0, list.size() - 1)));
        assertEquals(expectedSubList, ListUtil.subList(list, asIntegerSet(0, list.size() - 1, 2)));
    }

}

package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
        ListUtil.subList(null, new HashSet<>(Arrays.asList(0)));
    }

    @Test
    public void subList_nullIndicesReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Set<Integer> nullReference = null;
        ListUtil.subList(Arrays.asList(1, "spam"), nullReference);
    }

    @Test
    public void subList_nullIndicesElements_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(Arrays.asList("eggs"), new HashSet<>(Arrays.asList((Integer) null)));
    }

    @Test
    public void subList_outOfBoundsIndices_assertionError() {
        List<Object> list = Arrays.asList("spam", 6, new ArrayList<>());
        thrown.expect(AssertionError.class);
        ListUtil.subList(list, new HashSet<>(Arrays.asList(list.size() + 1)));
    }

    @Test
    public void subList_emptyList_assertionError() {
        thrown.expect(AssertionError.class);
        ListUtil.subList(Collections.emptyList(), new HashSet<>(Arrays.asList(0)));
    }

    @Test
    public void subList_emptyIndices_emptyResult() {
        assertEquals(Collections.emptyList(), ListUtil.subList(Arrays.asList("eggs"), Collections.emptySet()));
    }

    @Test
    public void subList_nullListElements_correctResult() {
        List<Object> containingNull = Arrays.asList(null, "spam", 5);
        List<Object> expectedList = Arrays.asList((Object) null);

        assertEquals(expectedList, ListUtil.subList(containingNull, new HashSet<>(Arrays.asList(0))));
    }

    @Test
    public void subList_validValues_correctResult() {
        List<Object> list = Arrays.asList("spam", 5, "eggs", "ham", new ArrayList<>());
        List<Object> expectedList = Arrays.asList("spam", "eggs", new ArrayList<>());

        // indices in sorted order
        assertEquals(expectedList, ListUtil.subList(list, new HashSet<>(Arrays.asList(0, 2, list.size() - 1))));

        // indices unsorted but list order maintained
        assertEquals(expectedList, ListUtil.subList(list, new HashSet<>(Arrays.asList(list.size() - 1, 2, 0))));
    }

    @Test
    public void areIndicesWithinBounds_nullListReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.areIndicesWithinBounds(null, Arrays.asList(0));
    }

    @Test
    public void areIndicesWithinBounds_nullIndicesReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.areIndicesWithinBounds(Arrays.asList(0, 1, 2), null);
    }

    @Test
    public void areIndicesWithinBounds_nullIndicesElements_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.areIndicesWithinBounds(Arrays.asList("spam", 5, 6), Arrays.asList(0, null));
    }

    @Test
    public void areIndicesWithinBounds_correctResult() {
        // empty list
        assertFalse(ListUtil.areIndicesWithinBounds(Collections.emptyList(), Arrays.asList(0)));

        // empty indices collection
        assertTrue(ListUtil.areIndicesWithinBounds(Arrays.asList(5), Collections.emptyList()));

        // null list elements are accepted
        List<Object> list = Arrays.asList(null, "eggs", "ham");
        assertTrue(ListUtil.areIndicesWithinBounds(list, Arrays.asList(0, 1, 2)));
        assertFalse(ListUtil.areIndicesWithinBounds(list, Arrays.asList(1, 0, list.size())));

        // non-null list elements
        list = Arrays.asList(1, new Object(), new ArrayList<>());
        // valid indices within bounds
        assertTrue(ListUtil.areIndicesWithinBounds(list, Arrays.asList(0, 1, 2)));
        // invalid index at the front
        assertFalse(ListUtil.areIndicesWithinBounds(list, Arrays.asList(-1, 0, list.size() - 1)));
        // invalid index at the middle
        assertFalse(ListUtil.areIndicesWithinBounds(list, Arrays.asList(1, -1, list.size() - 1)));
        // invalid index at the end
        assertFalse(ListUtil.areIndicesWithinBounds(list, Arrays.asList(1, 0, list.size())));
    }

}

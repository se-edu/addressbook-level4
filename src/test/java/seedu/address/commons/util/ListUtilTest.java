package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ListUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /*
     * subList tests:
     *
     * Equivalence partitions
     *     - list is null
     *     - indices null
     *     - indices non-null but elements null
     *     - empty indices list
     *     - no arguments for indices
     *     - out of bounds indices
     *     - empty list
     *     - list, indices non-null and elements null
     *     - list, indices and elements non-null
     */

    @Test
    public void subListVarargs_nullListReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(null, 0);
    }

    @Test
    public void subListCollection_nullListReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(null, Arrays.asList(0));
    }

    @Test
    public void subListVarargs_nullIndicesReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(Arrays.asList(1, "spam"), (Integer[]) null);
    }

    @Test
    public void subListCollection_nullIndicesReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(Arrays.asList("eggs"), (Collection<Integer>) null);
    }

    @Test
    public void subListVarargs_nullIndicesElements_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(Arrays.asList("ham", 3), 0, null);
    }

    @Test
    public void subListCollection_nullIndicesElements_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ListUtil.subList(Arrays.asList("eggs"), Arrays.asList((Integer) null));
    }

    @Test
    public void subListVarargs_outOfBoundsIndices_assertionError() {
        List<Object> list = Arrays.asList("spam", 6, new ArrayList<>());
        thrown.expect(AssertionError.class);
        ListUtil.subList(list, list.size() + 1);
    }

    @Test
    public void subListCollection_outOfBoundsIndices_assertionError() {
        List<Object> list = Arrays.asList("spam", 6, new ArrayList<>());
        thrown.expect(AssertionError.class);
        ListUtil.subList(list, Arrays.asList(list.size() + 1));
    }

    @Test
    public void subListVarargs_emptyList_assertionError() {
        thrown.expect(AssertionError.class);
        ListUtil.subList(Collections.emptyList(), 0);
    }

    @Test
    public void subListCollection_emptyList_assertionError() {
        thrown.expect(AssertionError.class);
        ListUtil.subList(Collections.emptyList(), Arrays.asList(0));
    }

    @Test
    public void subList_emptyIndices_emptyResult() {
        assertEquals(Collections.emptyList(), ListUtil.subList(Arrays.asList("eggs"), Collections.emptyList()));
        assertEquals(Collections.emptyList(), ListUtil.subList(Arrays.asList(1, "ham", 5)));
    }

    @Test
    public void subList_nullElements_correctResult() {
        List<Object> containingNull = Arrays.asList(null, "spam", 5);
        List<Object> expectedList = Arrays.asList((Object) null);

        assertEquals(expectedList, ListUtil.subList(containingNull, 0));
        assertEquals(expectedList, ListUtil.subList(containingNull, Arrays.asList(0)));
    }

    @Test
    public void subList_nonNullElements_correctResult() {
        List<Object> list = Arrays.asList("spam", 5, "eggs", "ham", new ArrayList<>());
        List<Object> expectedList = Arrays.asList("spam", "eggs", new ArrayList<>());

        // duplicate indices ignored
        assertEquals(expectedList, ListUtil.subList(list, 0, 0, 2, 2, list.size() - 1, list.size() - 1));
        assertEquals(expectedList, ListUtil.subList(list, Arrays.asList(0, 0, 2, 2, list.size() - 1, list.size() - 1)));

        // list order maintained
        assertEquals(expectedList, ListUtil.subList(list, list.size() - 1, 2, 0));
        assertEquals(expectedList, ListUtil.subList(list, Arrays.asList(list.size() - 1, 2, 0)));
    }

    /*
     * areIndicesWithinBounds tests:
     *
     * Equivalence partitions
     *     - list is null
     *     - indices null
     *     - indices non-null but elements null
     *     - empty indices list
     *     - empty list
     *     - list non-null but elements null
     *     - other valid values
     */

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

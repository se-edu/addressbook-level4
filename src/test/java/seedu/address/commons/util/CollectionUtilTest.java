package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CollectionUtilTest {
    @Test
    public void isAnyNull() {
        // empty list
        assertFalse(CollectionUtil.isAnyNull());

        // Any non-empty list
        assertFalse(CollectionUtil.isAnyNull(new Object(), new Object()));
        assertFalse(CollectionUtil.isAnyNull("test"));
        assertFalse(CollectionUtil.isAnyNull(""));

        // non empty list with just one at the beginning
        assertTrue(CollectionUtil.isAnyNull((Object) null));
        assertTrue(CollectionUtil.isAnyNull(null, "", new Object()));
        assertTrue(CollectionUtil.isAnyNull(null, new Object(), new Object()));

        // non empty list with nulls in the middle
        assertTrue(CollectionUtil.isAnyNull(new Object(), null, null, "test"));
        assertTrue(CollectionUtil.isAnyNull("", null, new Object()));

        // non empty list with one null as the last element
        assertTrue(CollectionUtil.isAnyNull("", new Object(), null));
        assertTrue(CollectionUtil.isAnyNull(new Object(), new Object(), null));

        // test whether method peeks into the elements inside container
        List<Object> nullList = Arrays.asList((Object) null);
        assertFalse(CollectionUtil.isAnyNull(nullList));
    }

    @Test
    public void elementsAreUnique() throws Exception {
        // empty list
        assertAreUnique();

        // only one object
        assertAreUnique((Object) null);
        assertAreUnique(1);
        assertAreUnique("");
        assertAreUnique("abc");

        // all objects unique
        assertAreUnique("abc", "ab", "a");
        assertAreUnique(1, 2);

        // some identical objects
        assertNotUnique("abc", "abc");
        assertNotUnique("abc", "", "abc", "ABC");
        assertNotUnique("", "abc", "a", "abc");

        assertNotUnique(1, new Integer(1));
        assertNotUnique(null, 1, new Integer(1));

        assertNotUnique(null, null);
        assertNotUnique(null, "a", "b", null);
    }

    private void assertAreUnique(Object... objects) {
        assertTrue(CollectionUtil.elementsAreUnique(Arrays.asList(objects)));
    }

    private void assertNotUnique(Object... objects) {
        assertFalse(CollectionUtil.elementsAreUnique(Arrays.asList(objects)));
    }
}

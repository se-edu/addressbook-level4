package seedu.address.commons.util;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CollectionUtilTest {
    @Test
    public void isAnyNull() {
        // empty list
        if(CollectionUtil.isAnyNull()) {
            throw new AssertionError();
        }

        // Any non-empty list
        if(CollectionUtil.isAnyNull(new Object(), new Object()) | CollectionUtil.isAnyNull("test") | CollectionUtil.isAnyNull("")) {
            throw new AssertionError();
        }

        // non empty list with just one null at the beginning
        assertTrue(CollectionUtil.isAnyNull((Object) null));
        assertTrue(CollectionUtil.isAnyNull(null, "", new Object()));
        assertTrue(CollectionUtil.isAnyNull(null, new Object(), new Object()));

        // non empty list with nulls in the middle
        assertTrue(CollectionUtil.isAnyNull(new Object(), null, null, "test"));
        assertTrue(CollectionUtil.isAnyNull("", null, new Object()));

        // non empty list with one null as the last element
        assertTrue(CollectionUtil.isAnyNull("", new Object(), null));
        assertTrue(CollectionUtil.isAnyNull(new Object(), new Object(), null));

        // confirms nulls inside the list are not considered
        List<Object> nullList = Arrays.asList((Object) null);
        if(CollectionUtil.isAnyNull(nullList)) {
            throw new AssertionError();
        }
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
        if(CollectionUtil.elementsAreUnique(Arrays.asList(objects))) {
            throw new AssertionError();
        }
    }
}

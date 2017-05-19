package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.util.CollectionUtil.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class CollectionUtilTest {
    @Test
    public void requireNonNullVarargs() {
        // no arguments
        assertNullPointerExceptionNotThrown();

        // any non-empty argument list
        assertNullPointerExceptionNotThrown(new Object(), new Object());
        assertNullPointerExceptionNotThrown("test");
        assertNullPointerExceptionNotThrown("");

        // argument lists with just one null at the beginning
        assertNullPointerExceptionThrown((Object) null);
        assertNullPointerExceptionThrown(null, "", new Object());
        assertNullPointerExceptionThrown(null, new Object(), new Object());

        // argument lists with nulls in the middle
        assertNullPointerExceptionThrown(new Object(), null, null, "test");
        assertNullPointerExceptionThrown("", null, new Object());

        // argument lists with one null as the last argument
        assertNullPointerExceptionThrown("", new Object(), null);
        assertNullPointerExceptionThrown(new Object(), new Object(), null);

        // null reference
        assertNullPointerExceptionThrown((Object[]) null);

        // confirms nulls inside lists in the argument list are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertNullPointerExceptionNotThrown(containingNull, new Object());
    }

    @Test
    public void requireNonNullCollection() {
        // lists containing nulls in the front
        assertNullPointerExceptionThrown(Arrays.asList((Object) null));
        assertNullPointerExceptionThrown(Arrays.asList(null, new Object(), ""));

        // lists containing nulls in the middle
        assertNullPointerExceptionThrown(Arrays.asList("spam", null, new Object()));
        assertNullPointerExceptionThrown(Arrays.asList("spam", null, "eggs", null, new Object()));

        // lists containing nulls at the end
        assertNullPointerExceptionThrown(Arrays.asList("spam", new Object(), null));
        assertNullPointerExceptionThrown(Arrays.asList(new Object(), null));

        // null reference
        assertNullPointerExceptionThrown((Collection<Object>) null);

        // empty list
        assertNullPointerExceptionNotThrown(Collections.emptyList());

        // list with all non-null elements
        assertNullPointerExceptionNotThrown(Arrays.asList(new Object(), "ham", new Integer(1)));
        assertNullPointerExceptionNotThrown(Arrays.asList(new Object()));

        // confirms nulls inside nested lists are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertNullPointerExceptionNotThrown(Arrays.asList(containingNull, new Object()));
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

    private void assertNullPointerExceptionThrown(Object... objects) {
        try {
            requireNonNull(objects);
            fail();
        } catch (NullPointerException npe) {
            // expected behavior
        }
    }

    private void assertNullPointerExceptionThrown(Collection<?> objects) {
        try {
            requireNonNull(objects);
            fail();
        } catch (NullPointerException npe) {
            // expected behavior
        }
    }

    private void assertNullPointerExceptionNotThrown(Object... objects) {
        requireNonNull(objects);
    }

    private void assertNullPointerExceptionNotThrown(Collection<?> objects) {
        requireNonNull(objects);
    }

    private void assertAreUnique(Object... objects) {
        assertTrue(CollectionUtil.elementsAreUnique(Arrays.asList(objects)));
    }

    private void assertNotUnique(Object... objects) {
        assertFalse(CollectionUtil.elementsAreUnique(Arrays.asList(objects)));
    }
}

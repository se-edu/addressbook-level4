package seedu.address.commons.util;

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

public class CollectionUtilTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isAnyNullVarargs() {
        // empty argument
        assertFalse(CollectionUtil.isAnyNull());

        // Any non-empty argument
        assertFalse(CollectionUtil.isAnyNull(new Object(), new Object()));
        assertFalse(CollectionUtil.isAnyNull("test"));
        assertFalse(CollectionUtil.isAnyNull(""));

        // non empty arguments with just one null at the beginning
        assertTrue(CollectionUtil.isAnyNull((Object) null));
        assertTrue(CollectionUtil.isAnyNull(null, "", new Object()));
        assertTrue(CollectionUtil.isAnyNull(null, new Object(), new Object()));

        // non empty arguments with nulls in the middle
        assertTrue(CollectionUtil.isAnyNull(new Object(), null, null, "test"));
        assertTrue(CollectionUtil.isAnyNull("", null, new Object()));

        // non empty arguments with one null as the last argument
        assertTrue(CollectionUtil.isAnyNull("", new Object(), null));
        assertTrue(CollectionUtil.isAnyNull(new Object(), new Object(), null));

        // confirms nulls inside the list are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertFalse(CollectionUtil.isAnyNull(containingNull, new Object()));
    }

    @Test
    public void isAnyNullVarargs_nullReference_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        CollectionUtil.isAnyNull((Object[]) null);
    }

    @Test
    public void isAnyNullCollection() {
        // list containing null at the front
        Collection<Object> frontNull = new ArrayList<>();
        frontNull.add(null);
        frontNull.add(new Object());
        frontNull.add("");
        assertTrue(CollectionUtil.isAnyNull(frontNull));

        // list containing null in the middle
        Collection<Object> middleNull = new ArrayList<>();
        middleNull.add("asd");
        middleNull.add(null);
        middleNull.add(new Object());
        assertTrue(CollectionUtil.isAnyNull(middleNull));

        // list containing null at the end
        Collection<Object> endNull = new ArrayList<>();
        endNull.add("xbcbxc");
        endNull.add(new Object());
        endNull.add(null);
        assertTrue(CollectionUtil.isAnyNull(endNull));

        // empty list
        assertFalse(CollectionUtil.isAnyNull(Collections.emptyList()));

        // valid non-empty list
        Collection<Object> validList = new ArrayList<>();
        validList.add(new Object());
        validList.add("asdadasd");
        validList.add(new Integer(1));
        assertFalse(CollectionUtil.isAnyNull(validList));
    }

    @Test
    public void isAnyNullCollection_nullReference_throwsNullPointerException() {
        Collection<Object> nullList = null;
        thrown.expect(NullPointerException.class);
        CollectionUtil.isAnyNull(nullList);
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

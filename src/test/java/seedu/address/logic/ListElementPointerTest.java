package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ListElementPointerTest {
    private final String first = "first";
    private final String second = "second";
    private final List<String> pointerElements = Arrays.asList(first, second);
    private ListElementPointer pointer;

    @Test
    public void constructor_noIndexSpecified_iteratesSuccessfully() {
        pointer = new ListElementPointer(pointerElements);
        assertPointerNextSuccess(first, second);
        assertPointerNextFailure();

        assertPointerPreviousSuccess(second, first);
        assertPointerPreviousFailure();
    }

    @Test
    public void constructor_indexSpecified_iteratesSuccessfully() {
        pointer = new ListElementPointer(pointerElements, 0);
        assertPointerNextSuccess(second);
        assertPointerPreviousSuccess(first);
    }

    /**
     * Calls {@code pointer#next()} for {@code elements.length} number of times,
     * with each time checking that {@code pointer#hasNext()} returns true and the
     * return value of {@code pointer#next()} equals to the element.
     */
    private void assertPointerNextSuccess(String... elements) {
        for (String element : elements) {
            assertTrue(pointer.hasNext());
            assertEquals(element, pointer.next());
        }
    }

    /**
     * Calls {@code pointer#previous()} for {@code elements.length} number of times,
     * with each time checking that {@code pointer#hasPrevious()} returns true and the
     * return value of {@code pointer#previous()} equals to the element.
     */
    private void assertPointerPreviousSuccess(String... elements) {
        for (String element : elements) {
            assertTrue(pointer.hasPrevious());
            assertEquals(element, pointer.previous());
        }
    }

    /**
     * Asserts that {@code pointer#hasNext()} returns false and the following
     * {@code pointer#next()} call throws {@code ArrayIndexOutOfBoundsException}.
     */
    private void assertPointerNextFailure() {
        assertFalse(pointer.hasNext());
        try {
            pointer.next();
            fail("The expected ArrayIndexOutOfBoundsException was not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected exception thrown
        }
    }

    /**
     * Asserts that {@code pointer#hasPrevious()} returns false and the following
     * {@code pointer#previous()} call throws {@code ArrayIndexOutOfBoundsException}.
     */
    private void assertPointerPreviousFailure() {
        assertFalse(pointer.hasPrevious());
        try {
            pointer.previous();
            fail("The expected ArrayIndexOutOfBoundsException was not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected exception thrown
        }
    }
}

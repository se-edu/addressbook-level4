package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ListElementPointerTest {
    private final String firstElement = "first";
    private final String secondElement = "second";
    private final List<String> pointerElements = Arrays.asList(firstElement, secondElement);
    private ListElementPointer pointer;

    @Test
    public void constructor_noIndexSpecified_iteratesSuccessfully() {
        pointer = new ListElementPointer<>(pointerElements);
        assertPointerNextSuccess(firstElement);
        assertPointerNextSuccess(secondElement);
        assertPointerNextFailure();

        assertPointerPreviousSuccess(secondElement);
        assertPointerPreviousSuccess(firstElement);
        assertPointerPreviousFailure();
    }

    @Test
    public void constructor_indexSpecified_iteratesSuccessfully() {
        pointer = new ListElementPointer<>(pointerElements, 0);
        assertPointerNextSuccess(secondElement);
        assertPointerPreviousSuccess(firstElement);
    }

    /**
     * Calls {@code pointer#next()} and checks that {@code pointer#hasNext()} returns true
     * and the return value of {@code pointer#next()} equals to {@code element}.
     */
    private void assertPointerNextSuccess(String element) {
        assertTrue(pointer.hasNext());
        assertEquals(element, pointer.next());
    }

    /**
     * Calls {@code pointer#previous()} and checks that {@code pointer#hasPrevious()} returns true
     * and the return value of {@code pointer#previous()} equals to {@code element}.
     */
    private void assertPointerPreviousSuccess(String element) {
        assertTrue(pointer.hasPrevious());
        assertEquals(element, pointer.previous());
    }

    /**
     * Asserts that {@code pointer#hasNext()} returns false and the following
     * {@code pointer#next()} call throws {@code ArrayIndexOutOfBoundsException}.
     */
    private void assertPointerNextFailure() {
        assertFalse(pointer.hasNext());
        try {
            pointer.next();
            fail("The expected IndexOutOfBoundsException was not thrown");
        } catch (IndexOutOfBoundsException e) {
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
            fail("The expected IndexOutOfBoundsException was not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected exception thrown
        }
    }
}

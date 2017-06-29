package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class ListElementPointerTest {
    private static final String FIRST_ELEMENT = "first";
    private static final String SECOND_ELEMENT = "second";
    private static final List<String> POINTER_ELEMENTS = Arrays.asList(FIRST_ELEMENT, SECOND_ELEMENT);
    private ListElementPointer pointer;

    @Test
    public void constructor_emptyList() {
        pointer = new ListElementPointer(Collections.emptyList());
        assertCurrentFailure();
        assertPreviousFailure();
        assertNextFailure();
    }

    @Test
    public void constructor_nonEmptyList() {
        pointer = new ListElementPointer(POINTER_ELEMENTS);

        assertCurrentSuccess(SECOND_ELEMENT);

        assertPreviousSuccess(FIRST_ELEMENT);
        assertPreviousFailure();

        assertCurrentSuccess(FIRST_ELEMENT);

        assertNextSuccess(SECOND_ELEMENT);
        assertNextFailure();

        assertCurrentSuccess(SECOND_ELEMENT);
    }

    @Test
    public void add() {
        pointer = new ListElementPointer(POINTER_ELEMENTS);
        String thirdElement = "third";
        pointer.add(thirdElement);

        assertCurrentSuccess(SECOND_ELEMENT);

        assertNextSuccess(thirdElement);
        assertNextFailure();

        assertPreviousSuccess(SECOND_ELEMENT);
        assertPreviousSuccess(FIRST_ELEMENT);
        assertPreviousFailure();
    }

    @Test
    public void equals() {
        ListElementPointer firstPointer = new ListElementPointer(Arrays.asList(FIRST_ELEMENT, SECOND_ELEMENT));

        // same object -> returns true
        assertTrue(firstPointer.equals(firstPointer));

        // same values -> returns true
        ListElementPointer firstPointerCopy = new ListElementPointer(Arrays.asList(FIRST_ELEMENT, SECOND_ELEMENT));
        assertTrue(firstPointer.equals(firstPointerCopy));

        // different types -> returns false
        assertFalse(firstPointer.equals(1));

        // null -> returns false
        assertFalse(firstPointer.equals(null));

        // different elements -> returns false
        ListElementPointer differentElementPointer = new ListElementPointer(Collections.singletonList(SECOND_ELEMENT));
        assertFalse(firstPointer.equals(differentElementPointer));

        // different index -> returns false
        firstPointerCopy.previous();
        assertFalse(firstPointer.equals(firstPointerCopy));
    }

    /**
     * Asserts that {@code pointer#hasNext()} returns true and the return value
     * of {@code pointer#next()} equals to {@code element}.
     */
    private void assertNextSuccess(String element) {
        assertTrue(pointer.hasNext());
        assertEquals(element, pointer.next());
    }

    /**
     * Asserts that {@code pointer#hasPrevious()} returns true and the return value
     * of {@code pointer#previous()} equals to {@code element}.
     */
    private void assertPreviousSuccess(String element) {
        assertTrue(pointer.hasPrevious());
        assertEquals(element, pointer.previous());
    }

    /**
     * Asserts that {@code pointer#hasCurrent()} returns true and the return value
     * of {@code pointer#current()} equals to {@code element}.
     */
    private void assertCurrentSuccess(String element) {
        assertTrue(pointer.hasCurrent());
        assertEquals(element, pointer.current());
    }

    /**
     * Asserts that {@code pointer#hasNext()} returns false and the following
     * {@code pointer#next()} call throws {@code NoSuchElementException}.
     */
    private void assertNextFailure() {
        assertFalse(pointer.hasNext());
        try {
            pointer.next();
            fail("The expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }

    /**
     * Asserts that {@code pointer#hasPrevious()} returns false and the following
     * {@code pointer#previous()} call throws {@code NoSuchElementException}.
     */
    private void assertPreviousFailure() {
        assertFalse(pointer.hasPrevious());
        try {
            pointer.previous();
            fail("The expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }

    /**
     * Asserts that {@code pointer#hasCurrent()} returns false and the following
     * {@code pointer#current()} call throws {@code NoSuchElementException}.
     */
    private void assertCurrentFailure() {
        assertFalse(pointer.hasCurrent());
        try {
            pointer.current();
            fail("The expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }
}

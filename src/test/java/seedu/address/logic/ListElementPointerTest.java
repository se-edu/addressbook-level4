package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class ListElementPointerTest {
    private static final String FIRST_ELEMENT = "first";
    private static final String SECOND_ELEMENT = "second";
    private List<String> pointerElements;
    private ListElementPointer pointer;

    @Before
    public void setUp() {
        pointerElements = new ArrayList<>();
        pointerElements.add(FIRST_ELEMENT);
        pointerElements.add(SECOND_ELEMENT);
    }

    @Test
    public void constructor_defensiveCopy_backingListUnmodified() {
        List<String> list = new ArrayList<>();
        pointer = new ListElementPointer(list);
        list.add(FIRST_ELEMENT);

        ListElementPointer emptyPointer = new ListElementPointer(Collections.emptyList());
        assertEquals(emptyPointer, pointer);
    }

    @Test
    public void emptyList() {
        pointer = new ListElementPointer(new ArrayList<>());
        assertCurrentFailure();
        assertPreviousFailure();
        assertNextFailure();

        pointer.add(FIRST_ELEMENT);
        assertNextSuccess(FIRST_ELEMENT);
    }

    @Test
    public void singleElementList() {
        List<String> list = new ArrayList<>();
        list.add(FIRST_ELEMENT);
        pointer = new ListElementPointer(list);

        assertCurrentSuccess(FIRST_ELEMENT);
        assertPreviousFailure();
        assertCurrentSuccess(FIRST_ELEMENT);
        assertNextFailure();
        assertCurrentSuccess(FIRST_ELEMENT);

        pointer.add(SECOND_ELEMENT);
        assertNextSuccess(SECOND_ELEMENT);
    }

    @Test
    public void multipleElementsList() {
        pointer = new ListElementPointer(pointerElements);
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
        ListElementPointer firstPointer = new ListElementPointer(pointerElements);

        // same object -> returns true
        assertTrue(firstPointer.equals(firstPointer));

        // same values -> returns true
        ListElementPointer firstPointerCopy = new ListElementPointer(pointerElements);
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
            throw new AssertionError("The expected NoSuchElementException was not thrown.");
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
            throw new AssertionError("The expected NoSuchElementException was not thrown.");
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
            throw new AssertionError("The expected NoSuchElementException was not thrown.");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }
}

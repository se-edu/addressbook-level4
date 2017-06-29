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

public class HistoryIteratorTest {
    private static final String FIRST_ELEMENT = "first";
    private static final String SECOND_ELEMENT = "second";
    private static final List<String> POINTER_ELEMENTS = Arrays.asList(SECOND_ELEMENT, FIRST_ELEMENT);
    private HistoryIterator iterator;

    @Test
    public void iteratesSuccessfully() {
        iterator = new HistoryIterator<>(POINTER_ELEMENTS);
        assertPointerCurrentSuccess(SECOND_ELEMENT);

        assertPointerPreviousSuccess(FIRST_ELEMENT);
        assertPointerPreviousFailure();

        assertPointerCurrentSuccess(FIRST_ELEMENT);

        assertPointerNextSuccess(SECOND_ELEMENT);
        assertPointerNextFailure();
    }

    @Test
    public void equals() {
        HistoryIterator<String> firstIterator = new HistoryIterator<>(Collections.singletonList(FIRST_ELEMENT));
        HistoryIterator<String> secondIterator = new HistoryIterator<>(Collections.singletonList(SECOND_ELEMENT));

        // same object -> returns true
        assertTrue(firstIterator.equals(firstIterator));

        // same values -> returns true
        HistoryIterator<String> firstIteratorCopy = new HistoryIterator<>(Collections.singletonList(FIRST_ELEMENT));
        assertTrue(firstIterator.equals(firstIteratorCopy));

        // different types -> returns false
        assertFalse(firstIterator.equals(1));

        // null -> returns false
        assertFalse(firstIterator.equals(null));

        // different person -> returns false
        assertFalse(firstIterator.equals(secondIterator));
    }

    /**
     * Calls {@code iterator#next()} and checks that {@code iterator#hasNext()} returns true
     * and the return value of {@code iterator#next()} equals to {@code element}.
     */
    private void assertPointerNextSuccess(String element) {
        assertTrue(iterator.hasNext());
        assertEquals(element, iterator.next());
    }

    /**
     * Calls {@code iterator#previous()} and checks that {@code iterator#hasPrevious()} returns true
     * and the return value of {@code iterator#previous()} equals to {@code element}.
     */
    private void assertPointerPreviousSuccess(String element) {
        assertTrue(iterator.hasPrevious());
        assertEquals(element, iterator.previous());
    }

    /**
     * Calls {@code iterator#current()} and checks that the return value equals to {@code element}.
     */
    private void assertPointerCurrentSuccess(String element) {
        assertEquals(element, iterator.current());
    }

    /**
     * Asserts that {@code iterator#hasNext()} returns false and the following
     * {@code iterator#next()} call throws {@code NoSuchElementException}.
     */
    private void assertPointerNextFailure() {
        assertFalse(iterator.hasNext());
        try {
            iterator.next();
            fail("The expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }

    /**
     * Asserts that {@code iterator#hasPrevious()} returns false and the following
     * {@code iterator#previous()} call throws {@code NoSuchElementException}.
     */
    private void assertPointerPreviousFailure() {
        assertFalse(iterator.hasPrevious());
        try {
            iterator.previous();
            fail("The expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }
}

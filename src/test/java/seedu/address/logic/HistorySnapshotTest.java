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

public class HistorySnapshotTest {
    private static final String FIRST_ELEMENT = "first";
    private static final String SECOND_ELEMENT = "second";
    private static final List<String> POINTER_ELEMENTS = Arrays.asList(SECOND_ELEMENT, FIRST_ELEMENT);
    private HistorySnapshot iterator;

    @Test
    public void constructor_emptyList() {
        iterator = new HistorySnapshot(Collections.emptyList());
        assertCurrentFailure();
        assertPreviousFailure();
        assertNextFailure();
    }

    @Test
    public void constructor_nonEmptyList() {
        List<String> copy = Arrays.asList(SECOND_ELEMENT, FIRST_ELEMENT);
        iterator = new HistorySnapshot(POINTER_ELEMENTS);
        assertEquals(copy, POINTER_ELEMENTS);

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
        iterator = new HistorySnapshot(POINTER_ELEMENTS);
        String thirdElement = "third";
        iterator.add(thirdElement);

        assertCurrentSuccess(SECOND_ELEMENT);

        assertNextSuccess(thirdElement);
        assertNextFailure();

        assertPreviousSuccess(SECOND_ELEMENT);
        assertPreviousSuccess(FIRST_ELEMENT);
        assertPreviousFailure();
    }

    @Test
    public void equals() {
        HistorySnapshot firstIterator = new HistorySnapshot(Collections.singletonList(FIRST_ELEMENT));
        HistorySnapshot secondIterator = new HistorySnapshot(Collections.singletonList(SECOND_ELEMENT));

        // same object -> returns true
        assertTrue(firstIterator.equals(firstIterator));

        // same values -> returns true
        HistorySnapshot firstIteratorCopy = new HistorySnapshot(Collections.singletonList(FIRST_ELEMENT));
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
    private void assertNextSuccess(String element) {
        assertTrue(iterator.hasNext());
        assertEquals(element, iterator.next());
    }

    /**
     * Calls {@code iterator#previous()} and checks that {@code iterator#hasPrevious()} returns true
     * and the return value of {@code iterator#previous()} equals to {@code element}.
     */
    private void assertPreviousSuccess(String element) {
        assertTrue(iterator.hasPrevious());
        assertEquals(element, iterator.previous());
    }

    /**
     * Calls {@code iterator#current()} and checks that {@code iterator#hasCurrent()} returns true
     * and the return value of {@code iterator#current()} equals to {@code element}.
     */
    private void assertCurrentSuccess(String element) {
        assertTrue(iterator.hasCurrent());
        assertEquals(element, iterator.current());
    }

    /**
     * Asserts that {@code iterator#hasNext()} returns false and the following
     * {@code iterator#next()} call throws {@code NoSuchElementException}.
     */
    private void assertNextFailure() {
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
    private void assertPreviousFailure() {
        assertFalse(iterator.hasPrevious());
        try {
            iterator.previous();
            fail("The expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }

    /**
     * Asserts that {@code iterator#hasCurrent()} returns false and the following
     * {@code iterator#current()} call throws {@code NoSuchElementException}.
     */
    private void assertCurrentFailure() {
        assertFalse(iterator.hasCurrent());
        try {
            iterator.current();
            fail("The expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }
}

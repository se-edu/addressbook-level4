package seedu.address.logic;

import java.util.ListIterator;

/**
 * This wrapper class disables the alternating functionality of the normal {@code ListIterator}.
 * This makes calling {@code #next()} and {@code #previous()} more intuitive for the caller.
 *
 * In the original {@code ListIterator}, calling alternating {@code ListIterator#next()} and
 * {@code ListIterator#previous()} will return the same value.
 * @see ListIterator#previous()
 * @see ListIterator#next()
 */
public class NonAlternatingListIterator<T> {
    private ListIterator<T> iterator;
    private boolean wasPreviousCalledPreviously;
    private boolean wasNextCalledPreviously;

    public NonAlternatingListIterator(ListIterator<T> iterator) {
        this.iterator = iterator;
    }

    /**
     * Returns true if {@code iterator} has a previous element in the list.
     * @see ListIterator#hasPrevious()
     */
    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }

    /**
     * Returns true if {@code iterator} has a next element in the list.
     * @see ListIterator#hasNext()
     */
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Returns the previous element in the list and moves the cursor position backwards.
     */
    public T previous() {
        if (wasNextCalledPreviously) {
            iterator.previous();
        }

        wasPreviousCalledPreviously = true;
        wasNextCalledPreviously = false;
        return iterator.previous();
    }

    /**
     * Returns the next element in the list and advances the cursor position.
     */
    public T next() {
        if (wasPreviousCalledPreviously) {
            iterator.next();
        }

        wasNextCalledPreviously = true;
        wasPreviousCalledPreviously = false;
        return iterator.next();
    }
}

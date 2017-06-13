package seedu.address.logic;

import java.util.ListIterator;

/**
 * Wraps around {@code ListIterator} as calling alternating {@code ListIterator#next()} and
 * {@code ListIterator#previous()} will return the same value. This wrapper class disables
 * this functionality.
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

    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public T previous() {
        if (wasNextCalledPreviously) {
            iterator.previous();
        }

        wasPreviousCalledPreviously = true;
        wasNextCalledPreviously = false;
        return iterator.previous();
    }

    public T next() {
        if (wasPreviousCalledPreviously) {
            iterator.next();
        }

        wasNextCalledPreviously = true;
        wasPreviousCalledPreviously = false;
        return iterator.next();
    }
}

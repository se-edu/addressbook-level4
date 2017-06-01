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
    private boolean isPreviousCalledPreviously;
    private boolean isNextCalledPreviously;

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
        if (isNextCalledPreviously) {
            iterator.previous();
        }

        isPreviousCalledPreviously = true;
        isNextCalledPreviously = false;
        return iterator.previous();
    }

    public T next() {
        if (isPreviousCalledPreviously) {
            iterator.next();
        }

        isNextCalledPreviously = true;
        isPreviousCalledPreviously = false;
        return iterator.next();
    }
}

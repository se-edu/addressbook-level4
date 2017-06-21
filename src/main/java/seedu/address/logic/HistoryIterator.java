package seedu.address.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Points to the last element in the {@code list}, and is able to iterate through the list.
 * This is different from {@code ListIterator}, which has a cursor that points in between elements.
 * The {@code ListIterator}'s behaviour is as such: when making alternating calls of {@code ListIterator#next()} and
 * {@code ListIterator#previous()}, the same element is returned on both calls.
 * However, {@code HistoryIterator}'s behaviour is as such: when making alternating calls of {@code next()} and
 * {@code previous()}, the next and previous elements are returned respectively.
 */
public class HistoryIterator<T> {
    private List<T> list;
    private int index;

    public HistoryIterator(List<T> list) {
        Collections.reverse(list);
        this.list = new ArrayList<>(list);
        index = list.size() - 1;
    }

    public void add(T element) {
        list.add(element);
    }

    /**
     * Returns true if calling {@code #next()} does not throw an {@code NoSuchElementException}.
     */
    public boolean hasNext() {
        int upIndex = index + 1;
        return isWithinBounds(upIndex);
    }

    /**
     * Returns true if calling {@code #previous()} does not throw an {@code NoSuchElementException}.
     */
    public boolean hasPrevious() {
        int downIndex = index - 1;
        return isWithinBounds(downIndex);
    }

    /**
     * Returns true if calling {@code #previous()} does not throw an {@code NoSuchElementException}.
     */
    public boolean hasCurrent() {
        return isWithinBounds(index);
    }

    private boolean isWithinBounds(int index) {
        return index >= 0 && index < list.size();
    }

    /**
     * Returns the next element in the list.
     * @throws NoSuchElementException if there is no more next element in the list.
     */
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(++index);
    }

    /**
     * Returns the previous element in the list.
     * @throws NoSuchElementException if there is no more previous element in the list.
     */
    public T previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        return list.get(--index);
    }

    /**
     * Returns the current element in the list.
     * @throws NoSuchElementException if there is no more previous element in the list.
     */
    public T current() {
        if (!hasCurrent()) {
            throw new NoSuchElementException();
        }
        return list.get(index);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HistoryIterator)) {
            return false;
        }

        // state check
        HistoryIterator iterator = (HistoryIterator) other;
        return list.equals(iterator.list) && index == iterator.index;
    }
}

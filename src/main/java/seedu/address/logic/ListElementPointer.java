package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Has a cursor that points to an element in the list, and is able to iterate through the list.
 * This is different from {@code ListIterator}, which has a cursor that points in between elements.
 * The {@code ListIterator}'s behaviour: when making alternating calls of {@code next()} and
 * {@code previous()}, the same element is returned on both calls.
 * In contrast, {@code ListElementPointer}'s behaviour: when making alternating calls of
 * {@code next()} and {@code previous()}, the next and previous elements are returned respectively.
 */
public class ListElementPointer {
    private List<String> list;
    private int index;

    /**
     * Constructs {@code ListElementPointer} which is backed by a defensive copy of {@code list}.
     * The cursor points to the last element in {@code list}.
     */
    public ListElementPointer(List<String> list) {
        this.list = new ArrayList<>(list);
        index = this.list.size() - 1;
    }

    /**
     * Appends {@code element} to the end of the list.
     */
    public void add(String element) {
        list.add(element);
    }

    /**
     * Returns true if calling {@code #next()} does not throw an {@code NoSuchElementException}.
     */
    public boolean hasNext() {
        int nextIndex = index + 1;
        return isWithinBounds(nextIndex);
    }

    /**
     * Returns true if calling {@code #previous()} does not throw an {@code NoSuchElementException}.
     */
    public boolean hasPrevious() {
        int previousIndex = index - 1;
        return isWithinBounds(previousIndex);
    }

    /**
     * Returns true if calling {@code #current()} does not throw an {@code NoSuchElementException}.
     */
    public boolean hasCurrent() {
        return isWithinBounds(index);
    }

    private boolean isWithinBounds(int index) {
        return index >= 0 && index < list.size();
    }

    /**
     * Returns the next element in the list and advances the cursor position.
     * @throws NoSuchElementException if there is no more next element in the list.
     */
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(++index);
    }

    /**
     * Returns the previous element in the list and moves the cursor position backwards.
     * @throws NoSuchElementException if there is no more previous element in the list.
     */
    public String previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        return list.get(--index);
    }

    /**
     * Returns the current element in the list.
     * @throws NoSuchElementException if the list is empty.
     */
    public String current() {
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
        if (!(other instanceof ListElementPointer)) {
            return false;
        }

        // state check
        ListElementPointer iterator = (ListElementPointer) other;
        return list.equals(iterator.list) && index == iterator.index;
    }
}

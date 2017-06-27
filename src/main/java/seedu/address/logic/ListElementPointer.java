package seedu.address.logic;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * Points to the element in the {@code list} at {@code index}, and is able to iterate through the list.
 * This is different from {@code ListIterator}, which has a cursor that points in between elements.
 * As such, while calling {@code ListIterator#next()} and {@code ListIterator#previous()} which returns the same
 * element on both calls, calling {@code next()} and {@code previous()} returns the next and previous elements
 * respectively.
 */
public class ListElementPointer<T> {
    private List<T> list;
    private int index;

    public ListElementPointer(List<T> list) {
        this(list, -1);
    }

    public ListElementPointer(List<T> list, int index) {
        checkArgument(index >= -1 && index <= list.size());
        this.list = new ArrayList<>(list);
        this.index = index;
    }

    /**
     * Returns true if calling {@code #next()} does not throw an {@code IndexOutOfBoundsException}.
     */
    public boolean hasNext() {
        int upIndex = index + 1;
        return isWithinBounds(upIndex);
    }

    /**
     * Returns true if calling {@code #previous()} does not throw an {@code IndexOutOfBoundsException}.
     */
    public boolean hasPrevious() {
        int downIndex = index - 1;
        return isWithinBounds(downIndex);
    }

    private boolean isWithinBounds(int index) {
        return index >= 0 && index < list.size();
    }

    /**
     * Returns the next element in the list.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public T next() {
        return list.get(++index);
    }

    /**
     * Returns the previous element in the list.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public T previous() {
        return list.get(--index);
    }
}

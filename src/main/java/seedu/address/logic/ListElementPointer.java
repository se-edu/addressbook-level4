package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Points to the element in the {@code list} at {@code index}, and is able to iterate through the list.
 */
public class ListElementPointer<T> {
    private List<T> list;
    private int index;

    public ListElementPointer(List<T> list) {
        this(list, -1);
    }

    public ListElementPointer(List<T> list, int index) {
        this.list = new ArrayList<>(list);
        this.index = index;
    }

    public boolean hasNext() {
        int upIndex = index + 1;
        return isWithinBounds(upIndex);
    }

    public boolean hasPrevious() {
        int downIndex = index - 1;
        return isWithinBounds(downIndex);
    }

    private boolean isWithinBounds(int index) {
        return index >= 0 && index < list.size();
    }

    public T next() {
        return list.get(++index);
    }

    public T previous() {
        return list.get(--index);
    }
}

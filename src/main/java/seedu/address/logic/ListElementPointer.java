package seedu.address.logic;

import java.util.List;

import com.sun.javafx.UnmodifiableArrayList;

/**
 * Points to the element in the {@code list} at {@code index}, and is able to iterate through the list.
 */
public class ListElementPointer {
    private UnmodifiableArrayList<String> list;
    private int index;

    public ListElementPointer(List<String> list) {
        this(list, -1);
    }

    public ListElementPointer(List<String> list, int index) {
        this.list = new UnmodifiableArrayList<>(list.toArray(new String[0]), list.size());
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

    public String next() {
        return list.get(++index);
    }

    public String previous() {
        return list.get(--index);
    }
}

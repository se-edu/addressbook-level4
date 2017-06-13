package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class NonAlternatingListIteratorTest {
    private static final NonAlternatingListIterator<Integer> iterator;

    static {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        iterator = new NonAlternatingListIterator<>(list.listIterator());
    }

    @Test
    public void previousAndNext() {
        Integer one = 1;
        Integer two = 2;
        Integer three = 3;

        assertEquals(one, iterator.next());
        assertEquals(two, iterator.next());
        assertEquals(three, iterator.next());
        assertEquals(two, iterator.previous());
        assertEquals(three, iterator.next());
        assertEquals(two, iterator.previous());
        assertEquals(one, iterator.previous());
    }
}

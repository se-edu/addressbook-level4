package seedu.address.logic;

import static org.junit.Assert.assertTrue;

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
        assertTrue(iterator.next() == 1);
        assertTrue(iterator.next() == 2);
        assertTrue(iterator.next() == 3);
        assertTrue(iterator.previous() == 2);
        assertTrue(iterator.next() == 3);
        assertTrue(iterator.previous() == 2);
        assertTrue(iterator.previous() == 1);
    }
}

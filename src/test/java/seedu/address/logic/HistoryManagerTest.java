package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class HistoryManagerTest {
    private History history;

    @Before
    public void setUp() {
        history = new HistoryManager();
    }

    @Test
    public void add() {
        String clear = "clear";
        String addsBob = "adds Bob";

        history.add(clear);
        history.add(addsBob);
        assertEquals(Arrays.asList(clear, addsBob), history.getHistory());
    }
}

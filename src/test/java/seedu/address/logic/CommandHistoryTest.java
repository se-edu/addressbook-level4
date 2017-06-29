package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class CommandHistoryTest {
    private CommandHistory history;

    @Before
    public void setUp() {
        history = new CommandHistory();
    }

    @Test
    public void add() {
        final String validCommand = "clear";
        final String invalidCommand = "adds Bob";

        history.addFirst(validCommand);
        history.addFirst(invalidCommand);
        HistoryIterator<String> expectedIterator = new HistoryIterator<>(Arrays.asList(invalidCommand, validCommand));
        assertEquals(expectedIterator, history.getHistory());
    }
}

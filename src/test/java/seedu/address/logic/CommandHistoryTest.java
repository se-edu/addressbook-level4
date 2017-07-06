package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;

public class CommandHistoryTest {
    private CommandHistory history;

    @Before
    public void setUp() {
        history = new CommandHistory();
    }

    @Test
    public void add() {
        final CommandObject validCommand = new CommandObject("clear", new ClearCommand());
        final CommandObject invalidCommand = new CommandObject("adds Bob", null);

        history.add(validCommand);
        history.add(invalidCommand);
        assertEquals(Arrays.asList(validCommand, invalidCommand), history.getHistory());
    }
}

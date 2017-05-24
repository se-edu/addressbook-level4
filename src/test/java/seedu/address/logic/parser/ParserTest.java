package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.HistoryCommand;

public class ParserTest {
    private final Parser parser = new Parser();

    @Test
    public void parseCommand_history() {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);

        //TODO: unintended behavior
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        assertFalse(parser.parseCommand("histories") instanceof HistoryCommand);
    }
}

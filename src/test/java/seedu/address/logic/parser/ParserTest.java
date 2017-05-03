package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * A Test class for the parsing of commands.
 */
public abstract class ParserTest {
    private Parser parser = new Parser();

    protected void assertParseFailure(String userInput, String expectedMessage) {
        Command command = parser.parseCommand(userInput);

        // The current implementation of the parsing of commands will always return
        // an IncorrectCommand if the parsing failed.
        assertTrue(command instanceof IncorrectCommand);
        IncorrectCommand incorrectCommand = (IncorrectCommand) command;

        assertEquals(expectedMessage, incorrectCommand.feedbackToUser);
    }

    protected void assertParseSuccess(String userInput) {
        Command command = parser.parseCommand(userInput);

        assertFalse(command instanceof IncorrectCommand);
    }
}

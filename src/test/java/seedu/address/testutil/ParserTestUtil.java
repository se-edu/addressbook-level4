package seedu.address.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.parser.Parser;

/**
 * Static utility class for parser tests.
 */
public class ParserTestUtil {
    private static Parser parser = new Parser();

    public static void assertParseFailure(String userInput, String expectedMessage) {
        Command command = parser.parseCommand(userInput);

        // The current implementation of the parsing of commands will always return
        // an IncorrectCommand if the parsing failed.
        if (!(command instanceof IncorrectCommand)) {
            fail();
        }

        IncorrectCommand incorrectCommand = (IncorrectCommand) command;
        assertEquals(expectedMessage, incorrectCommand.feedbackToUser);
    }

    public static void assertParseSuccess(String userInput) {
        Command command = parser.parseCommand(userInput);

        if (command instanceof IncorrectCommand) {
            fail();
        }
    }
}

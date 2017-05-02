package seedu.address.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.parser.Parser;

public class ParserTestUtil {
    private static Parser parser = new Parser();

    public static void assertParseFailure(String userInput, String expectedMessage) {
        Command command = parser.parseCommand(userInput);

        if (!(command instanceof IncorrectCommand)) {
            fail();
        }

        IncorrectCommand incorrectCommand = (IncorrectCommand) command;
        assertEquals(expectedMessage, incorrectCommand.feedbackToUser);
    }
}

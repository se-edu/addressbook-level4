package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.GoalCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

public class GoalCommandParserTest {
    private GoalCommandParser parser = new GoalCommandParser();

    @Test
    public void parse_validCommand_success() {
        String userInput = "4.5";
        GoalCommand expectedCommand = new GoalCommand(4.5);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidNumberFormat_failure() {
        String userInput = "4.5 3.5";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoalCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}

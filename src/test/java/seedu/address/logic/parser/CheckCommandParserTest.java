package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AMY;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CheckCommand;

public class CheckCommandParserTest {
    private CheckCommandParser parser = new CheckCommandParser();
    private final String nonEmptyMode = "in";

    @Test
    public void parse_indexSpecified_success() {
        // have mode
        String userInput = PREFIX_NRIC + VALID_NRIC_AMY + " " + PREFIX_PASSWORD
                + VALID_PASSWORD_AMY + " " + PREFIX_MODE + nonEmptyMode;
        CheckCommand expectedCommand = new CheckCommand(VALID_NRIC_AMY, VALID_PASSWORD_AMY, nonEmptyMode);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no mode
        userInput = PREFIX_NRIC + VALID_NRIC_AMY + " " + PREFIX_PASSWORD
                + VALID_PASSWORD_AMY + " " + PREFIX_MODE;
        expectedCommand = new CheckCommand(VALID_NRIC_AMY, VALID_PASSWORD_AMY, "");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, CheckCommand.COMMAND_WORD, expectedMessage);

        // no name
        assertParseFailure(parser, CheckCommand.COMMAND_WORD + " " + VALID_PASSWORD_AMY + nonEmptyMode, expectedMessage);
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFiletypes.FILETYPE_CSV;

import org.junit.Test;

import seedu.address.logic.commands.ExportAllCommand;

//@@author jitwei98
public class ExportAllCommandParserTest {

    private ExportAllCommandParser parser = new ExportAllCommandParser();

    @Test
    public void parseFiletypeSuccess() {
        String userInput = FILETYPE_CSV;
        ExportAllCommand expectedCommand = new ExportAllCommand(userInput);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseMissingNotNullFieldFailure() {
        // no parameters
        String userInput = ExportAllCommand.COMMAND_WORD + " ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAllCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

}

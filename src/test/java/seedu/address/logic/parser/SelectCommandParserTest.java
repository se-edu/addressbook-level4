package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SelectCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() throws Exception {
        assertParseSuccess(parser, "1", new SelectCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}

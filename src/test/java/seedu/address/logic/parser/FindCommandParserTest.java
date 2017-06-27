package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        try {
            parser.parse("     ");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), pe.getMessage());
        }
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws Exception {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new HashSet<>(Arrays.asList("Alice", "Bob")));
        FindCommand actualFindCommand = parser.parse("Alice Bob");
        assertEquals(expectedFindCommand, actualFindCommand);

        // multiple whitespaces between keywords
        actualFindCommand = parser.parse(" \n Alice \n \t Bob  \t");
        assertEquals(expectedFindCommand, actualFindCommand);
    }

}

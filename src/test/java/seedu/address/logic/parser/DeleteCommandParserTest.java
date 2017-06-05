package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() throws Exception {
        DeleteCommand command = parser.parse("1");
        assertEquals(INDEX_FIRST_PERSON, command.targetIndex);
        command = parser.parse("  1 ");
        assertEquals(INDEX_FIRST_PERSON, command.targetIndex);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        assertParseFailure("a");
        assertParseFailure("1 abc");
    }

    private void assertParseFailure(String args) {
        try {
            parser.parse(args);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe.getMessage());
        }
    }
}

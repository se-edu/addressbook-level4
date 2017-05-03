package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class EditCommandParserTest {

    private Parser parser = new Parser();

    @Test
    public void parse_failure() {
        // no index specified
        assertParseFailure("edit Bobby", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // no fields specified
        assertParseFailure("edit 1", EditCommand.MESSAGE_NOT_EDITED);

        // invalid values
        assertParseFailure("edit 1 *&", Name.MESSAGE_NAME_CONSTRAINTS);

        assertParseFailure("edit 1 p/abcd", Phone.MESSAGE_PHONE_CONSTRAINTS);

        assertParseFailure("edit 1 e/yahoo!!!", Email.MESSAGE_EMAIL_CONSTRAINTS);

        assertParseFailure("edit 1 a/", Address.MESSAGE_ADDRESS_CONSTRAINTS);

        assertParseFailure("edit 1 t/*&", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_success() {
        // all fields specified
        assertParseSuccess("edit 2 Bobby p/91234567 e/bobby@example.com a/Block 123, Bobby Street 3 t/husband");

        // some fields specified
        assertParseSuccess("edit 1 t/sweetie p/91234567 t/bestie");

        // one field specified & remove all tags
        assertParseSuccess("edit 3 t/");
    }

    /**
     * Asserts {@code userInput} is unsuccessfully parsed and the error message
     * equals to {@code expectedMessage}
     */
    protected void assertParseFailure(String userInput, String expectedMessage) {
        Command command = parser.parseCommand(userInput);

        // The current implementation of the parsing of commands will always return
        // an IncorrectCommand if the parsing failed.
        assertTrue(command instanceof IncorrectCommand);
        IncorrectCommand incorrectCommand = (IncorrectCommand) command;

        assertEquals(expectedMessage, incorrectCommand.feedbackToUser);
    }

    /**
     * Asserts {@code userInput} is successfully parsed
     */
    protected void assertParseSuccess(String userInput) {
        Command command = parser.parseCommand(userInput);

        assertFalse(command instanceof IncorrectCommand);
    }
}

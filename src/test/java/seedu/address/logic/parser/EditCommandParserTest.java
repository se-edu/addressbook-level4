package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Before;
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

    private EditCommandParser parser;

    @Before
    public void setUp() {
        parser = new EditCommandParser();
    }

    @Test
    public void parse_validArgs_returnsEditCommand() {
        // all fields specified
        assertSuccess("1 Bobby p/91234567 e/bobby@gmail.com a/Block 123, Bobby Street 3 t/husband");

        // not all fields specified
        assertSuccess("1 t/sweetie t/bestie");

        // clear tags
        assertSuccess("1 t/");
    }

    @Test
    public void parse_noPersonIndex_returnsIncorrectCommand() {
        String expectedFeedback = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertFailure("Bobby", expectedFeedback);
    }

    @Test
    public void parse_invalidValues_returnsIncorrectCommand() {
        assertFailure("1 *&", Name.MESSAGE_NAME_CONSTRAINTS);
        assertFailure("1 p/abcd", Phone.MESSAGE_PHONE_CONSTRAINTS);
        assertFailure("1 e/yahoo!!!", Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertFailure("1 a/", Address.MESSAGE_ADDRESS_CONSTRAINTS);
        assertFailure("1 t/*&", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_noFieldsSpecified_returnsIncorrectCommand() {
        assertFailure("1", EditCommand.MESSAGE_NOT_EDITED);
    }

    private void assertSuccess(String args) {
        Command command = parser.parse(args);
        assertTrue(command instanceof EditCommand);
    }

    private void assertFailure(String args, String expectedFeedback) {
        Command command = parser.parse(args);
        assertTrue(command instanceof IncorrectCommand);
        assertEquals(expectedFeedback, ((IncorrectCommand) command).feedbackToUser);
    }

}

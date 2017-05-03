package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.EditCommandTestUtil.VALID_ADDRESS_ONE;
import static seedu.address.testutil.EditCommandTestUtil.VALID_EMAIL_ONE;
import static seedu.address.testutil.EditCommandTestUtil.VALID_EMAIL_TWO;
import static seedu.address.testutil.EditCommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.EditCommandTestUtil.VALID_PHONE_ONE;
import static seedu.address.testutil.EditCommandTestUtil.VALID_PHONE_TWO;
import static seedu.address.testutil.EditCommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.EditCommandTestUtil.VALID_TAG_HUSBAND;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditCommandTestUtil;

public class EditCommandParserTest {

    private static final String INVALID_PREFIX = "q/";

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_failure() {
        // no index specified
        assertParseFailure(VALID_NAME_AMY + " " + PREFIX_PHONE.getPrefix() + VALID_PHONE_ONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // negative index
        assertParseFailure("-5 " + VALID_NAME_AMY + " " + PREFIX_EMAIL.getPrefix() + VALID_EMAIL_ONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // no fields specified
        assertParseFailure("1", EditCommand.MESSAGE_NOT_EDITED);

        // invalid prefix specified - unknown field will be parsed as name
        assertParseFailure("1 " + INVALID_PREFIX + "unknown", Name.MESSAGE_NAME_CONSTRAINTS);

        // single invalid value
        assertParseFailure("1 *&", Name.MESSAGE_NAME_CONSTRAINTS);
        assertParseFailure("1 " + PREFIX_PHONE.getPrefix() + "abcd", Phone.MESSAGE_PHONE_CONSTRAINTS);
        assertParseFailure("1 " + PREFIX_EMAIL.getPrefix() + "yahoo!!!", Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertParseFailure("1 " + PREFIX_ADDRESS.getPrefix(), Address.MESSAGE_ADDRESS_CONSTRAINTS);
        assertParseFailure("1 " + PREFIX_TAG.getPrefix() + "*&", Tag.MESSAGE_TAG_CONSTRAINTS);

        // single invalid value with another valid value of a different field
        assertParseFailure("1 " + PREFIX_PHONE.getPrefix() + "abcd " + VALID_EMAIL_ONE + " ",
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // single invalid value with other valid values of the same field
        assertParseFailure("1 " + PREFIX_TAG.getPrefix() + VALID_TAG_HUSBAND + " " + PREFIX_TAG.getPrefix(),
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure("1 " + PREFIX_PHONE.getPrefix() + VALID_PHONE_TWO + " " + PREFIX_PHONE.getPrefix() + "abcd",
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // multiple invalid values
        assertParseFailure("1 *& " + PREFIX_ADDRESS.getPrefix(), Name.MESSAGE_NAME_CONSTRAINTS);

        // name not given as the first value, gets parsed as part of email, results in an invalid email
        assertParseFailure("1 " + PREFIX_EMAIL.getPrefix() + VALID_EMAIL_ONE + " " + VALID_NAME_AMY,
                Email.MESSAGE_EMAIL_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws Exception {
        String userInput = "2 " + VALID_NAME_AMY + " " + PREFIX_PHONE.getPrefix() + VALID_PHONE_TWO + " "
                + PREFIX_TAG.getPrefix() + VALID_TAG_HUSBAND + " " + PREFIX_EMAIL.getPrefix() + VALID_EMAIL_ONE + " "
                + PREFIX_ADDRESS.getPrefix() + VALID_ADDRESS_ONE + " " + PREFIX_TAG.getPrefix() + VALID_TAG_FRIEND;

        Optional<List<String>> tags = Optional.of(Arrays.asList(VALID_TAG_HUSBAND, VALID_TAG_FRIEND));

        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(VALID_NAME_AMY), Optional.of(VALID_PHONE_TWO), Optional.of(VALID_EMAIL_ONE),
                Optional.of(VALID_ADDRESS_ONE), tags);
        EditCommand expectedCommand = new EditCommand(2, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws Exception {
        String userInput = "1 " + PREFIX_PHONE.getPrefix() + VALID_PHONE_TWO + " " + PREFIX_EMAIL.getPrefix()
                + VALID_EMAIL_ONE;

        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(VALID_PHONE_TWO), Optional.of(VALID_EMAIL_ONE), Optional.empty(), Optional.empty());
        EditCommand expectedCommand = new EditCommand(1, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws Exception {
        String userInput = "3 " + VALID_NAME_AMY;

        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(Optional.of(VALID_NAME_AMY),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        EditCommand expectedCommand = new EditCommand(3, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws Exception {
        String userInput = "1 " + PREFIX_PHONE.getPrefix() + VALID_PHONE_TWO + " " + PREFIX_PHONE.getPrefix()
                + VALID_PHONE_ONE + " " + PREFIX_PHONE.getPrefix() + "911" + " " + PREFIX_EMAIL.getPrefix()
                + VALID_EMAIL_ONE + " " + PREFIX_EMAIL.getPrefix() + " " + VALID_EMAIL_TWO + " "
                + PREFIX_ADDRESS.getPrefix() + VALID_ADDRESS_ONE + " " + PREFIX_ADDRESS.getPrefix() + VALID_ADDRESS_ONE;

        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of("911"), Optional.of(VALID_EMAIL_TWO), Optional.of(VALID_ADDRESS_ONE), Optional.empty());
        EditCommand expectedCommand = new EditCommand(1, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() throws Exception {
        String userInput = "3 " + PREFIX_TAG.getPrefix();

        Optional<List<String>> tags = Optional.of(Arrays.asList());
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), tags);
        EditCommand expectedCommand = new EditCommand(3, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    /**
     * Asserts the parsing of {@code userInput} is unsuccessful and the error message
     * equals to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        Command command = parser.parse(userInput);

        // The current implementation of the parsing of commands will always return
        // an IncorrectCommand if the parsing failed.
        assertTrue(command instanceof IncorrectCommand);
        IncorrectCommand incorrectCommand = (IncorrectCommand) command;

        assertEquals(expectedMessage, incorrectCommand.feedbackToUser);
    }

    /**
     * Asserts the parsing of {@code userInput} is successful and the result matches {@code expectedCommand}
     */
    private void assertParseSuccess(String userInput, EditCommand expectedCommand) {
        Command command = parser.parse(userInput);
        assert expectedCommand.equals(command);
    }
}

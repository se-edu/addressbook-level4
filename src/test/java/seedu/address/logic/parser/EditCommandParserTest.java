package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_failure() {
        // no index specified
        assertParseFailure(
                EditCommandTestUtil.VALID_NAME_ONE + " " + CliSyntax.PREFIX_PHONE.getPrefix()
                        + EditCommandTestUtil.VALID_PHONE_ONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // negative index
        assertParseFailure(
                "-5 " + EditCommandTestUtil.VALID_NAME_ONE + " " + CliSyntax.PREFIX_EMAIL.getPrefix()
                        + EditCommandTestUtil.VALID_EMAIL_ONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // no fields specified
        assertParseFailure("1", EditCommand.MESSAGE_NOT_EDITED);

        // unknown field specified - unknown field will be parsed as name
        assertParseFailure("1 q/unknown", Name.MESSAGE_NAME_CONSTRAINTS);

        // only invalid values
        assertParseFailure("1 *&", Name.MESSAGE_NAME_CONSTRAINTS);
        assertParseFailure("1 " + CliSyntax.PREFIX_PHONE.getPrefix() + "abcd", Phone.MESSAGE_PHONE_CONSTRAINTS);
        assertParseFailure("1 " + CliSyntax.PREFIX_EMAIL.getPrefix() + "yahoo!!!", Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertParseFailure("1 " + CliSyntax.PREFIX_ADDRESS.getPrefix(), Address.MESSAGE_ADDRESS_CONSTRAINTS);
        assertParseFailure("1 " + CliSyntax.PREFIX_TAG.getPrefix() + "*&", Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid values with valid values of different type
        assertParseFailure(
                "1 " + CliSyntax.PREFIX_PHONE.getPrefix() + "abcd " + EditCommandTestUtil.VALID_EMAIL_ONE + " ",
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid values with valid values of same type
        assertParseFailure("1 " + CliSyntax.PREFIX_TAG.getPrefix() + EditCommandTestUtil.VALID_TAG_HUSBAND + " "
                + CliSyntax.PREFIX_TAG.getPrefix(), Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure("1 " + CliSyntax.PREFIX_PHONE.getPrefix() + EditCommandTestUtil.VALID_PHONE_TWO + " "
                + CliSyntax.PREFIX_PHONE.getPrefix() + "abcd", Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid ordering - entire string parsed as email
        assertParseFailure("1 " + CliSyntax.PREFIX_EMAIL.getPrefix() + EditCommandTestUtil.VALID_EMAIL_ONE + " "
                + EditCommandTestUtil.VALID_NAME_ONE,
                Email.MESSAGE_EMAIL_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = "2 " + EditCommandTestUtil.VALID_NAME_ONE + " " + CliSyntax.PREFIX_PHONE.getPrefix()
                + EditCommandTestUtil.VALID_PHONE_TWO + " " + CliSyntax.PREFIX_TAG.getPrefix()
                + EditCommandTestUtil.VALID_TAG_HUSBAND + " " + CliSyntax.PREFIX_EMAIL.getPrefix()
                + EditCommandTestUtil.VALID_EMAIL_ONE + " " + CliSyntax.PREFIX_ADDRESS.getPrefix()
                + EditCommandTestUtil.VALID_ADDRESS_ONE + " " + CliSyntax.PREFIX_TAG.getPrefix()
                + EditCommandTestUtil.VALID_TAG_HUBBY;

        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE),
                Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);

        EditCommand expectedCommand = new EditCommand(2, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = "1 " + CliSyntax.PREFIX_PHONE.getPrefix() + EditCommandTestUtil.VALID_PHONE_TWO + " "
                + CliSyntax.PREFIX_EMAIL.getPrefix() + EditCommandTestUtil.VALID_EMAIL_ONE;

        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(EditCommandTestUtil.VALID_PHONE_TWO), Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE),
                Optional.empty(), Optional.empty());
        EditCommand expectedCommand = new EditCommand(1, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_repeatedFieldsSpecified_acceptsLast() {
        String userInput = "1 " + CliSyntax.PREFIX_PHONE.getPrefix() + EditCommandTestUtil.VALID_PHONE_TWO + " "
                + CliSyntax.PREFIX_PHONE.getPrefix() + EditCommandTestUtil.VALID_PHONE_ONE;

        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(EditCommandTestUtil.VALID_PHONE_ONE), Optional.empty(), Optional.empty(), Optional.empty());
        EditCommand expectedCommand = new EditCommand(1, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        String userInput = "3 " + EditCommandTestUtil.VALID_NAME_ONE;

        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        EditCommand expectedCommand = new EditCommand(3, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        String userInput = "3 " + CliSyntax.PREFIX_TAG.getPrefix();

        Optional<List<String>> tags = Optional.of(Arrays.asList());
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), tags);
        EditCommand expectedCommand = new EditCommand(3, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    /**
     * Asserts {@code userInput} is unsuccessfully parsed and the error message
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
     * Asserts {@code userInput} is successfully parsed
     */
    private void assertParseSuccess(String userInput, EditCommand expectedCommand) {
        Command command = parser.parse(userInput);
        assert expectedCommand.equals(command);
    }
}

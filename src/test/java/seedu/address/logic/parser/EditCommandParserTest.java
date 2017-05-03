package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class EditCommandParserTest {

    private static String VALID_NAME = "Bobby ";
    private static String VALID_PHONE_ONE = "98765432 ";
    private static String VALID_PHONE_TWO = "91234567 ";
    private static String VALID_EMAIL = "bobby@example.com ";
    private static String VALID_ADDRESS = "Block 123, Bobby Street 3 ";
    private static String VALID_TAG_ONE = "husband ";
    private static String VALID_TAG_TWO = "hubby ";

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_failure() {
        // no index specified
        assertParseFailure(VALID_NAME + CliSyntax.PREFIX_PHONE.getPrefix() + VALID_PHONE_ONE, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // negative index
        assertParseFailure("-5 " + VALID_NAME + CliSyntax.PREFIX_EMAIL.getPrefix() + VALID_EMAIL,
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
        assertParseFailure("1 " + CliSyntax.PREFIX_PHONE.getPrefix() + "abcd " + VALID_EMAIL,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid values with valid values of same type
        assertParseFailure("1 " + CliSyntax.PREFIX_TAG.getPrefix() + VALID_TAG_ONE + CliSyntax.PREFIX_TAG.getPrefix(),
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure("1 " + CliSyntax.PREFIX_PHONE.getPrefix() + VALID_PHONE_TWO
                + CliSyntax.PREFIX_PHONE.getPrefix() + "abcd", Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid ordering - entire string parsed as email
        assertParseFailure("1 " + CliSyntax.PREFIX_EMAIL.getPrefix() + VALID_EMAIL + VALID_NAME,
                Email.MESSAGE_EMAIL_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = "2 " + VALID_NAME + CliSyntax.PREFIX_PHONE.getPrefix() + VALID_PHONE_TWO
                + CliSyntax.PREFIX_TAG.getPrefix() + VALID_TAG_ONE + CliSyntax.PREFIX_EMAIL.getPrefix() + VALID_EMAIL
                + CliSyntax.PREFIX_ADDRESS.getPrefix() + VALID_ADDRESS + CliSyntax.PREFIX_TAG.getPrefix()
                + VALID_TAG_TWO;

        Optional<List<String>> tags = Optional.of(Arrays.asList(VALID_TAG_ONE, VALID_TAG_TWO));
        EditPersonDescriptor descriptor = parseEditPersonDescriptor(Optional.of(VALID_NAME),
                Optional.of(VALID_PHONE_TWO), Optional.of(VALID_EMAIL), Optional.of(VALID_ADDRESS.trim()), tags);
        // should we auto trim addresses (see 1 line above)
        EditCommand expectedCommand = new EditCommand(2, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = "1 " + CliSyntax.PREFIX_PHONE.getPrefix() + VALID_PHONE_TWO
                + CliSyntax.PREFIX_EMAIL.getPrefix() + VALID_EMAIL;

        EditPersonDescriptor descriptor = parseEditPersonDescriptor(Optional.empty(), Optional.of(VALID_PHONE_TWO),
                Optional.of(VALID_EMAIL), Optional.empty(), Optional.empty());
        EditCommand expectedCommand = new EditCommand(1, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_unintendedBehavior_success() {
        String userInput = "1 " + CliSyntax.PREFIX_PHONE.getPrefix() + VALID_PHONE_TWO
                + CliSyntax.PREFIX_PHONE.getPrefix() + VALID_PHONE_ONE;

        EditPersonDescriptor descriptor = parseEditPersonDescriptor(Optional.empty(), Optional.of(VALID_PHONE_ONE),
                Optional.empty(), Optional.empty(), Optional.empty());
        EditCommand expectedCommand = new EditCommand(1, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        String userInput = "3 " + VALID_NAME;

        EditPersonDescriptor descriptor = parseEditPersonDescriptor(Optional.of(VALID_NAME), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
        EditCommand expectedCommand = new EditCommand(3, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        String userInput = "3 " + CliSyntax.PREFIX_TAG.getPrefix();

        Optional<List<String>> tags = Optional.of(Arrays.asList());
        EditPersonDescriptor descriptor = parseEditPersonDescriptor(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), tags);
        EditCommand expectedCommand = new EditCommand(3, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    private EditPersonDescriptor parseEditPersonDescriptor(Optional<String> name, Optional<String> phone,
            Optional<String> email, Optional<String> address, Optional<List<String>> tags) {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        try {
            descriptor.setName(ParserUtil.parseName(name));
            descriptor.setPhone(ParserUtil.parsePhone(phone));
            descriptor.setEmail(ParserUtil.parseEmail(email));
            descriptor.setAddress(ParserUtil.parseAddress(address));
            if (tags.isPresent()) {
                descriptor.setTags(Optional.of(ParserUtil.parseTags(tags.get())));
            }
        } catch (IllegalValueException ive) {
            fail();
        }

        return descriptor;
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

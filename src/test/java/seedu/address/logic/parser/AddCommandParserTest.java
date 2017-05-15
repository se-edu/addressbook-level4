package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.StringJoiner;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {

    private static final String VALID_PHONE_VALUE = "123456";
    private static final String VALID_EMAIL_VALUE = "123@email.com";
    private static final String VALID_ADDRESS_VALUE = "123 Street";
    private static final String VALID_TAG_VALUE = "tag";

    private static final String VALID_NAME = "Bob Choo";
    private static final String VALID_PHONE = " " + PREFIX_PHONE + VALID_PHONE_VALUE;
    private static final String VALID_EMAIL = " " + PREFIX_EMAIL + VALID_EMAIL_VALUE;
    private static final String VALID_ADDRESS = " " + PREFIX_ADDRESS + VALID_ADDRESS_VALUE;
    private static final String VALID_TAG = " " + PREFIX_TAG + VALID_TAG_VALUE;

    private static final String INVALID_NAME = "James&";
    private static final String INVALID_PHONE = " " + PREFIX_PHONE + "+651234";
    private static final String INVALID_EMAIL = " " + PREFIX_EMAIL + "example.com";
    private static final String INVALID_ADDRESS = " " + PREFIX_ADDRESS + " ";
    private static final String INVALID_TAG = " " + PREFIX_TAG + "#friends";

    @Test
    public void parse_missingFields_returnsIncorrectCommand() throws Exception {
        // All fields missing
        assertParseFailure("", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // Email missing
        assertParseFailure(VALID_NAME + INVALID_PHONE + INVALID_ADDRESS + VALID_TAG,
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // Phone, address and tag missing
        assertParseFailure(INVALID_NAME + VALID_EMAIL,
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTag_returnsAddCommand() throws Exception {
        Person personToAdd = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE_VALUE)
                .withEmail(VALID_EMAIL_VALUE).withAddress(VALID_ADDRESS_VALUE).build();

        assertParseSuccess(VALID_NAME + VALID_PHONE + VALID_EMAIL + VALID_ADDRESS,
                            new AddCommand(personToAdd));
    }

    @Test
    public void parse_oneInvalidField_returnsIncorrectCommand() throws Exception {
        // Invalid name
        assertParseFailure(INVALID_NAME + VALID_PHONE + VALID_EMAIL + VALID_ADDRESS + VALID_TAG,
                            Name.MESSAGE_NAME_CONSTRAINTS);

        // Invalid phone
        assertParseFailure(VALID_NAME + INVALID_PHONE + VALID_EMAIL + VALID_ADDRESS + VALID_TAG,
                            Phone.MESSAGE_PHONE_CONSTRAINTS);

        // Invalid email
        assertParseFailure(VALID_NAME + VALID_PHONE + INVALID_EMAIL + VALID_ADDRESS + VALID_TAG,
                            Email.MESSAGE_EMAIL_CONSTRAINTS);

        // Invalid address
        assertParseFailure(VALID_NAME + VALID_PHONE + VALID_EMAIL + INVALID_ADDRESS + VALID_TAG,
                            Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // Invalid tag
        assertParseFailure(VALID_NAME + VALID_PHONE + VALID_EMAIL + VALID_ADDRESS + INVALID_TAG,
                            Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_multipleInvalidFields_returnsIncorrectCommand() throws Exception {
        // Two invalid fields - phone and address
        StringJoiner expectedMessage = new StringJoiner(System.lineSeparator());
        expectedMessage.add(Phone.MESSAGE_PHONE_CONSTRAINTS).add(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        assertParseFailure(VALID_NAME + INVALID_PHONE + VALID_EMAIL + INVALID_ADDRESS + VALID_TAG,
                            expectedMessage.toString());

        // All invalid fields
        expectedMessage = new StringJoiner(System.lineSeparator());
        expectedMessage.add(Name.MESSAGE_NAME_CONSTRAINTS).add(Phone.MESSAGE_PHONE_CONSTRAINTS)
                        .add(Email.MESSAGE_EMAIL_CONSTRAINTS).add(Address.MESSAGE_ADDRESS_CONSTRAINTS)
                        .add(Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(INVALID_NAME + INVALID_PHONE + INVALID_EMAIL + INVALID_ADDRESS + INVALID_TAG,
                            expectedMessage.toString());

        // Random order for all fields
        assertParseFailure(INVALID_NAME + INVALID_TAG + INVALID_ADDRESS + INVALID_EMAIL + INVALID_PHONE,
                            expectedMessage.toString());
    }

    @Test
    public void parse_repeatedFields_returnsIncorrectCommand() throws Exception {
        // Valid phone followed by invalid phone
        assertParseFailure(VALID_NAME + VALID_PHONE + VALID_EMAIL + VALID_ADDRESS + VALID_TAG + INVALID_PHONE,
                            Phone.MESSAGE_PHONE_CONSTRAINTS);

        // Invalid tag followed by valid tag
        assertParseFailure(VALID_NAME + VALID_PHONE + INVALID_TAG + VALID_ADDRESS + VALID_EMAIL + VALID_TAG,
                            Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_allValidFields_returnsAddCommand() throws Exception {
        Person personToAdd = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE_VALUE)
                .withEmail(VALID_EMAIL_VALUE).withAddress(VALID_ADDRESS_VALUE).withTags(VALID_TAG_VALUE).build();

        assertParseSuccess(VALID_NAME + VALID_PHONE + VALID_EMAIL + VALID_ADDRESS + VALID_TAG,
                            new AddCommand(personToAdd));
    }

    /**
     * Asserts that parsing {@code userInput} was successful and the command returned
     * is equal to {@code expectedCommand}
     */
    private void assertParseSuccess(String userInput, Command expectedCommand) {
        Command actualCommand = new AddCommandParser().parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    /**
     * Asserts that parsing {@code userInput} was unsuccessful and the error message given
     * is equal to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        Command actualCommand = new AddCommandParser().parse(userInput);
        assertTrue(((IncorrectCommand) actualCommand).feedbackToUser.equals(expectedMessage));
    }
}

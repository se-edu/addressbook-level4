package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    private static final String INVALID_NAME = "$*%";
    private static final String INVALID_PHONE = " p/+651234";
    private static final String INVALID_EMAIL = " e/email.com";
    private static final String INVALID_ADDRESS = " a/ ";
    private static final String INVALID_TAG = " t/$";
    private static final String VALID_NAME = "Name";
    private static final String VALID_PHONE = " p/123456";
    private static final String VALID_EMAIL = " e/123@email.com";
    private static final String VALID_ADDRESS = " a/123 Street";
    private static final String VALID_TAG = " t/tag";

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
        // Two invalid fields - phone and email
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
    public void parse_allValidFields_returnsAddCommand() throws Exception {
        Person personToAdd = new PersonBuilder().withName("Name").withPhone("123456")
                .withEmail("123@email.com").withAddress("123 Street").withTags("tag").build();

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

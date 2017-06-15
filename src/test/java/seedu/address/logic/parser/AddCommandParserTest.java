package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.testutil.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.testutil.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.testutil.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.testutil.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.testutil.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.testutil.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.testutil.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.testutil.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.testutil.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.testutil.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.testutil.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.testutil.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.testutil.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.testutil.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.testutil.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.testutil.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.testutil.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.testutil.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.testutil.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws Exception {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(AddCommand.COMMAND_WORD + NAME_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }

    /**
     * Asserts that the parsing of {@code userInput} is successful and the {@code AddCommand} created
     * equals to {@code expectedCommand}
     */
    private void assertParseSuccess(String userInput, AddCommand expectedCommand) throws Exception {
        AddCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    /**
     * Asserts that the parsing of {@code userInput} is unsuccessful and the error message
     * equals to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}

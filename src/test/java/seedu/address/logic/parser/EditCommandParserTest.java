package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.EditCommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.testutil.EditCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.EditCommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.testutil.EditCommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.testutil.EditCommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.EditCommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.testutil.EditCommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.EditCommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.EditCommandTestUtil.VALID_TAG_HUSBAND;

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
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String NAME_DESC_AMY = " " + VALID_NAME_AMY;
    private static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    private static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    private static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    private static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    private static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    private static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    private static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    private static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String INVALID_NAME_DESC = " *&";
    private static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "abcd";
    private static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "yahoo!!!";
    private static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS;
    private static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "*&";

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_noValidPrefix_everythingTakenAsName() throws Exception {
        // Unable to test whether complex strings such as "1 q/unknown is this k/parsed"
        // are entirely parsed as Name. Verification of Name field requires successful parsing.
        // However, "/" is an invalid value for Name, thus parsing invalid prefixes will always fail.
        // E.g, we cannot verify if "q/unknown" is parsed as Name, resulting in an error, or whether
        // "q/unknown is this k/parsed" is parsed as Name, which results in an error as well.
        String invalidPrefix = "q/";
        assertParseFailure("1 " + invalidPrefix + "unknown", Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure("1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure("", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure("-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero
        assertParseFailure("0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_singleInvalidValue_failure() {
        // invalid name
        assertParseFailure("1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure("1" + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure("1" + INVALID_EMAIL_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure("1" + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure("1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_singleInvalidValueWithOtherValidValues_failure() {
        // invalid phone followed by valid email
        assertParseFailure("1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // valid phone followed by invalid phone
        assertParseFailure("1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // valid tag followed by empty tag - while parsing {@code PREFIX_TAG} alone will reset the tags
        // of the {@code Person} being edited, however parsing it together with a valid tag results in error
        assertParseFailure("1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure("1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure("1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_multipleInvalidValues_failure() {
        assertParseFailure("1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_invalidArgumentOrdering_failure() {
        // name not given as the first value, gets parsed as part of email, results in an invalid email
        assertParseFailure("1" + EMAIL_DESC_AMY + NAME_DESC_AMY, Email.MESSAGE_EMAIL_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws Exception {
        int targetIndex = 2;
        String userInput = targetIndex + NAME_DESC_AMY + PHONE_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws Exception {
        int targetIndex = 1;
        String userInput = targetIndex + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws Exception {
        int targetIndex = 3;
        String userInput = targetIndex + NAME_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws Exception {
        int targetIndex = 1;
        String userInput = targetIndex + PHONE_DESC_AMY + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() throws Exception {
        int targetIndex = 3;
        String userInput = targetIndex + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    /**
     * Asserts the parsing of {@code userInput} is unsuccessful and the error message
     * equals to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        Command command = parser.parse(userInput);

        // Parsing of commands will always return an IncorrectCommand if the parsing failed.
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

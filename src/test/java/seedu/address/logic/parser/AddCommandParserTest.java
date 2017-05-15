package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.CommandTestUtil.INVALID_ADDRESS_JAMES;
import static seedu.address.testutil.CommandTestUtil.INVALID_EMAIL_JAMES;
import static seedu.address.testutil.CommandTestUtil.INVALID_NAME_JAMES;
import static seedu.address.testutil.CommandTestUtil.INVALID_PHONE_JAMES;
import static seedu.address.testutil.CommandTestUtil.INVALID_TAG_HUBBY;
import static seedu.address.testutil.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.testutil.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.testutil.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.testutil.CommandTestUtil.VALID_TAG_FRIEND;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class AddCommandParserTest {

    private static final String VALID_NAME = VALID_NAME_AMY;
    private static final String VALID_PHONE = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    private static final String VALID_EMAIL = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    private static final String VALID_ADDRESS = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    private static final String VALID_TAG = " " + PREFIX_TAG + VALID_TAG_FRIEND;

    private static final String INVALID_NAME = INVALID_NAME_JAMES;
    private static final String INVALID_PHONE = " " + PREFIX_PHONE + INVALID_PHONE_JAMES;
    private static final String INVALID_EMAIL = " " + PREFIX_EMAIL + INVALID_EMAIL_JAMES;
    private static final String INVALID_ADDRESS = " " + PREFIX_ADDRESS + INVALID_ADDRESS_JAMES;
    private static final String INVALID_TAG = " " + PREFIX_TAG + INVALID_TAG_HUBBY;

    @Test
    public void parse_oneInvalidField_failure() throws Exception {
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
    public void parse_multipleInvalidFields_failure() throws Exception {
        // Two invalid fields - phone and address
        assertParseFailure(VALID_NAME + INVALID_PHONE + VALID_EMAIL + INVALID_ADDRESS + VALID_TAG,
                            Arrays.asList(Phone.MESSAGE_PHONE_CONSTRAINTS, Address.MESSAGE_ADDRESS_CONSTRAINTS)
                                .stream().collect(Collectors.joining("\n")));

        // All invalid fields
        assertParseFailure(INVALID_NAME + INVALID_PHONE + INVALID_EMAIL + INVALID_ADDRESS + INVALID_TAG,
                            Arrays.asList(Name.MESSAGE_NAME_CONSTRAINTS, Phone.MESSAGE_PHONE_CONSTRAINTS,
                                    Email.MESSAGE_EMAIL_CONSTRAINTS, Address.MESSAGE_ADDRESS_CONSTRAINTS,
                                    Tag.MESSAGE_TAG_CONSTRAINTS).stream().collect(Collectors.joining("\n")));

        // Random order for all fields
        assertParseFailure(INVALID_NAME + INVALID_TAG + INVALID_ADDRESS + INVALID_EMAIL + INVALID_PHONE,
                            Arrays.asList(Name.MESSAGE_NAME_CONSTRAINTS, Phone.MESSAGE_PHONE_CONSTRAINTS,
                                    Email.MESSAGE_EMAIL_CONSTRAINTS, Address.MESSAGE_ADDRESS_CONSTRAINTS,
                                    Tag.MESSAGE_TAG_CONSTRAINTS).stream().collect(Collectors.joining("\n")));
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

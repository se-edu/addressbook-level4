package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;

/**
 * Utility class containing the constants required for tests related to EditCommand
 */
public class EditCommandTestUtil {
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + VALID_NAME_AMY;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;

    public static final EditPersonDescriptor DESC_AMY;
    public static final EditPersonDescriptor DESC_BOB;

    static {
        try {
            DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                    .withPhones(VALID_PHONE_AMY).withEmails(VALID_EMAIL_AMY).withAddresses(VALID_ADDRESS_AMY)
                    .withTags(VALID_TAG_FRIEND).build();
            DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                    .withPhones(VALID_PHONE_BOB).withEmails(VALID_EMAIL_BOB).withAddresses(VALID_ADDRESS_BOB)
                    .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        } catch (IllegalValueException ive) {
            throw new AssertionError("Method should not fail.");
        }
    }
}

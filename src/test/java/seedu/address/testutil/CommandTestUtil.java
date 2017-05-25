package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;

/**
 * Utility class containing the constants required for Command tests
 */
public class CommandTestUtil {
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

    public static final String INVALID_NAME_JAMES = "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_JAMES = "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_JAMES = "james!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_JAMES = ""; // empty string not allowed for addresses
    public static final String INVALID_TAG_HUBBY = "hubby*"; // '*' not allowed in tags

    public static final EditPersonDescriptor DESC_AMY;
    public static final EditPersonDescriptor DESC_BOB;

    static {
        try {
            DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                    .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                    .withTags(VALID_TAG_FRIEND).build();
            DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                    .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                    .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        } catch (IllegalValueException ive) {
            throw new AssertionError("Method should not fail.");
        }
    }
}

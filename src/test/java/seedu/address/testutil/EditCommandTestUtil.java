package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.ParserUtil;

/**
 * Utility class containing the methods required for tests related to EditCommand
 */
public class EditCommandTestUtil {
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_ONE = "98765432";
    public static final String VALID_PHONE_TWO = "91234567";
    public static final String VALID_EMAIL_ONE = "bobby@example.com";
    public static final String VALID_EMAIL_TWO = "amy@example.com";
    public static final String VALID_ADDRESS_ONE = "Block 123, Bobby Street 3";
    public static final String VALID_ADDRESS_TWO = "Block 312, Amy Street 1";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final List<String> STANDARD_TAGS_ONE = Arrays.asList(VALID_TAG_HUSBAND, VALID_TAG_FRIEND);
    public static final EditPersonDescriptor STANDARD_DESCRIPTION_AMY;
    public static final List<String> STANDARD_TAGS_TWO = Arrays.asList(VALID_TAG_HUSBAND);
    public static final EditPersonDescriptor STANDARD_DESCRIPTION_BOB;

    static {
        try {
            STANDARD_DESCRIPTION_AMY = createEditPersonDescriptor(Optional.of(VALID_NAME_AMY),
                    Optional.of(VALID_PHONE_ONE), Optional.of(VALID_EMAIL_ONE), Optional.of(VALID_ADDRESS_ONE),
                    Optional.of(STANDARD_TAGS_ONE));
            STANDARD_DESCRIPTION_BOB = createEditPersonDescriptor(Optional.of(VALID_NAME_BOB),
                    Optional.of(VALID_PHONE_TWO), Optional.empty(), Optional.of(VALID_ADDRESS_ONE),
                    Optional.of(STANDARD_TAGS_TWO));
        } catch (IllegalValueException ive) {
            throw new AssertionError("Method should not fail.");
        }
    }

    /**
     * Creates an {@code EditPersonDescriptor} from raw values. Specifying a field with an empty
     * Optional implies that the field will not be changed upon execution of {@code EditCommand}
     *
     * @throws IllegalValueException if any of the input values cannot be parsed
     **/
    public static EditPersonDescriptor createEditPersonDescriptor(Optional<String> name, Optional<String> phone,
            Optional<String> email, Optional<String> address, Optional<List<String>> tags)
            throws IllegalValueException {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();

        descriptor.setName(ParserUtil.parseName(name));
        descriptor.setPhone(ParserUtil.parsePhone(phone));
        descriptor.setEmail(ParserUtil.parseEmail(email));
        descriptor.setAddress(ParserUtil.parseAddress(address));
        if (tags.isPresent()) {
            descriptor.setTags(Optional.of(ParserUtil.parseTags(tags.get())));
        }

        return descriptor;
    }
}

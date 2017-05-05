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
    public static final String VALID_NAME_ONE = "Bobby";
    public static final String VALID_NAME_TWO = "Bub";
    public static final String VALID_PHONE_ONE = "98765432";
    public static final String VALID_PHONE_TWO = "91234567";
    public static final String VALID_EMAIL_ONE = "bobby@example.com";
    public static final String VALID_EMAIL_TWO = "bub@example.com";
    public static final String VALID_ADDRESS_ONE = "Block 123, Bobby Street 3";
    public static final String VALID_ADDRESS_TWO = "Block 123, Bub Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_HUBBY = "hubby";

    public static final Optional<List<String>> STANDARD_TAGS_ONE = Optional
            .of(Arrays.asList(VALID_TAG_HUSBAND, VALID_TAG_HUBBY));
    public static final EditPersonDescriptor STANDARD_DESCRIPTION_ONE;
    public static final Optional<List<String>> STANDARD_TAGS_TWO = Optional.of(Arrays.asList(VALID_TAG_HUSBAND));
    public static final EditPersonDescriptor STANDARD_DESCRIPTION_TWO;

    static {
        try {
            STANDARD_DESCRIPTION_ONE = createEditPersonDescriptor(Optional.of(VALID_NAME_ONE),
                    Optional.of(VALID_PHONE_ONE), Optional.of(VALID_EMAIL_ONE), Optional.of(VALID_ADDRESS_ONE),
                    STANDARD_TAGS_ONE);
            STANDARD_DESCRIPTION_TWO = createEditPersonDescriptor(Optional.of(VALID_NAME_TWO),
                    Optional.of(VALID_PHONE_TWO), Optional.empty(), Optional.of(VALID_ADDRESS_ONE), STANDARD_TAGS_TWO);
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

package seedu.address.testutil;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.ParserUtil;

/*
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
            .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
    public static final EditPersonDescriptor STANDARD_DESCRIPTION_ONE = EditCommandTestUtil.createEditPersonDescriptor(
            Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_ONE),
            Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
            STANDARD_TAGS_ONE);
    public static final Optional<List<String>> STANDARD_TAGS_TWO = Optional
            .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND));
    public static final EditPersonDescriptor STANDARD_DESCRIPTION_TWO = EditCommandTestUtil.createEditPersonDescriptor(
            Optional.of(EditCommandTestUtil.VALID_NAME_TWO), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
            Optional.empty(), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
            STANDARD_TAGS_TWO);

    /**
     * Creates an {@code EditPersonDescriptor} from raw values. Leaving a field
     * as optional implies that the field will not be changed upon execution of {@code EditCommand}
     **/
    public static EditPersonDescriptor createEditPersonDescriptor(Optional<String> name, Optional<String> phone,
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
}

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EditCommandTestUtil.STANDARD_DESCRIPTION_ONE;
import static seedu.address.testutil.EditCommandTestUtil.STANDARD_DESCRIPTION_TWO;
import static seedu.address.testutil.EditCommandTestUtil.STANDARD_TAGS_ONE;
import static seedu.address.testutil.EditCommandTestUtil.STANDARD_TAGS_TWO;
import static seedu.address.testutil.EditCommandTestUtil.VALID_ADDRESS_ONE;
import static seedu.address.testutil.EditCommandTestUtil.VALID_ADDRESS_TWO;
import static seedu.address.testutil.EditCommandTestUtil.VALID_EMAIL_ONE;
import static seedu.address.testutil.EditCommandTestUtil.VALID_EMAIL_TWO;
import static seedu.address.testutil.EditCommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.EditCommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.EditCommandTestUtil.VALID_PHONE_ONE;
import static seedu.address.testutil.EditCommandTestUtil.VALID_PHONE_TWO;

import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditCommandTestUtil;

public class EditPersonDescriptorTest {

    @Test
    public void equals_sameValues_returnsTrue() {
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(STANDARD_DESCRIPTION_ONE);

        assertTrue(STANDARD_DESCRIPTION_ONE.equals(descriptorWithSameValues));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(STANDARD_DESCRIPTION_ONE.equals(STANDARD_DESCRIPTION_ONE));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        assertFalse(STANDARD_DESCRIPTION_ONE.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(STANDARD_DESCRIPTION_ONE.equals(5));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        assertFalse(STANDARD_DESCRIPTION_ONE.equals(STANDARD_DESCRIPTION_TWO));
    }

    @Test
    public void equals_differentName_returnsFalse() throws Exception {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.of(VALID_NAME_AMY),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.of(VALID_NAME_BOB),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        assertFalse(descriptorOne.equals(descriptorTwo));
    }

    @Test
    public void equals_differentPhone_returnsFalse() throws Exception {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                    Optional.of(VALID_PHONE_ONE), Optional.empty(), Optional.empty(), Optional.empty());
        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                    Optional.of(VALID_PHONE_TWO), Optional.empty(), Optional.empty(), Optional.empty());

        assertFalse(descriptorOne.equals(descriptorTwo));
    }

    @Test
    public void equals_differentEmail_returnsFalse() throws Exception {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                    Optional.empty(), Optional.of(VALID_EMAIL_ONE), Optional.empty(), Optional.empty());
        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                    Optional.empty(), Optional.of(VALID_EMAIL_TWO), Optional.empty(), Optional.empty());

        assertFalse(descriptorOne.equals(descriptorTwo));
    }

    @Test
    public void equals_differentAddress_returnsFalse() throws Exception {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.of(VALID_ADDRESS_ONE), Optional.empty());
        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.of(VALID_ADDRESS_TWO), Optional.empty());

        assertFalse(descriptorOne.equals(descriptorTwo));
    }

    @Test
    public void equals_differentTags_returnsFalse() throws Exception {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.empty(), STANDARD_TAGS_ONE);
        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.empty(), STANDARD_TAGS_TWO);

        assertFalse(descriptorOne.equals(descriptorTwo));
    }
}

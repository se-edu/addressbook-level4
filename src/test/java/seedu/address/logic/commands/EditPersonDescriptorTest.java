package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditCommandTestUtil;

public class EditPersonDescriptorTest {

    @Test
    public void equals_sameValues_success() {
        EditPersonDescriptor differentDescriptor = new EditPersonDescriptor(EditCommandTestUtil.standardDescriptorOne);

        assertTrue(EditCommandTestUtil.standardDescriptorOne.equals(differentDescriptor));
    }

    @Test
    public void equals_sameObject_success() {
        assertTrue(EditCommandTestUtil.standardDescriptorOne.equals(EditCommandTestUtil.standardDescriptorOne));
    }

    @Test
    public void equals_nullObject_failure() {
        assertFalse(EditCommandTestUtil.standardDescriptorOne.equals(null));
    }

    @Test
    public void equals_differentObject_failure() {
        assertFalse(EditCommandTestUtil.standardDescriptorOne.equals(""));
    }

    @Test
    public void equals_differentValues_failure() {
        assertFalse(EditCommandTestUtil.standardDescriptorOne.equals(EditCommandTestUtil.standardDescriptorTwo));
    }

    @Test
    public void equals_differentName_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty());

        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_TWO), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty());

        assertFalse(descriptorOne.equals(descriptorTwo));
    }

    @Test
    public void equals_differentPhone_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(EditCommandTestUtil.VALID_PHONE_ONE), Optional.empty(), Optional.empty(), Optional.empty());

        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(EditCommandTestUtil.VALID_PHONE_TWO), Optional.empty(), Optional.empty(), Optional.empty());

        assertFalse(descriptorOne.equals(descriptorTwo));
    }

    @Test
    public void equals_differentEmail_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.empty(), Optional.empty());

        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.of(EditCommandTestUtil.VALID_EMAIL_TWO), Optional.empty(), Optional.empty());

        assertFalse(descriptorOne.equals(descriptorTwo));
    }

    @Test
    public void equals_differentAddress_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                Optional.empty());

        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.of(EditCommandTestUtil.VALID_ADDRESS_TWO),
                Optional.empty());

        assertFalse(descriptorOne.equals(descriptorTwo));
    }

    @Test
    public void equals_differentTags_failure() {
        Optional<List<String>> tagsOne = Optional.of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND));
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), tagsOne);

        Optional<List<String>> tagsTwo = Optional.of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), tagsTwo);

        assertFalse(descriptorOne.equals(descriptorTwo));
    }
}

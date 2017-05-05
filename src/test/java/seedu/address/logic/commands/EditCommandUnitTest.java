package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditCommandTestUtil;

/**
 * A unit test class that tests edit command's equals method.
 */
public class EditCommandUnitTest {
    @Test
    public void editCommand_sameVariables_success() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);
        EditCommand commandOne = new EditCommand(1, descriptorOne);

        EditPersonDescriptor descriptorTwo = new EditPersonDescriptor(descriptorOne);
        EditCommand commandTwo = new EditCommand(1, descriptorTwo);

        assertEquals(commandOne, commandTwo);
    }

    @Test
    public void editCommand_sameObject_success() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);
        EditCommand command = new EditCommand(1, descriptor);

        assertEquals(command, command);
    }

    @Test
    public void editCommand_nullObject_failure() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);
        EditCommand command = new EditCommand(1, descriptor);

        assertNotEquals(command, null);
    }

    @Test
    public void editCommand_differentObject_failure() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);
        EditCommand command = new EditCommand(1, descriptor);

        assertNotEquals(command, new ClearCommand());
    }

    @Test
    public void editCommand_differentVariables_failure() {
        Optional<List<String>> tagsOne = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tagsOne);
        EditCommand commandOne = new EditCommand(1, descriptorOne);

        Optional<List<String>> tagsTwo = Optional.of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND));
        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_TWO), Optional.of(EditCommandTestUtil.VALID_PHONE_ONE),
                Optional.empty(), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE), tagsTwo);
        EditCommand commandTwo = new EditCommand(2, descriptorTwo);

        assertNotEquals(commandOne, commandTwo);
    }

    @Test
    public void editCommand_differentIndex_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_TWO), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty());
        EditCommand commandOne = new EditCommand(1, descriptorOne);
        EditCommand commandTwo = new EditCommand(2, descriptorOne);

        assertNotEquals(commandOne, commandTwo);
    }

    @Test
    public void editCommand_differentDescriptor_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(EditCommandTestUtil.VALID_PHONE_ONE), Optional.empty(), Optional.empty(), Optional.empty());
        EditCommand commandOne = new EditCommand(1, descriptorOne);

        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(EditCommandTestUtil.VALID_PHONE_TWO), Optional.empty(), Optional.empty(), Optional.empty());
        EditCommand commandTwo = new EditCommand(1, descriptorTwo);

        assertNotEquals(commandOne, commandTwo);
    }

    @Test
    public void editPersonDescriptor_sameVariables_success() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);
        EditPersonDescriptor descriptorTwo = new EditPersonDescriptor(descriptorOne);

        assertEquals(descriptorOne, descriptorTwo);
    }

    @Test
    public void editPersonDescriptor_sameObject_success() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);

        assertEquals(descriptor, descriptor);
    }

    @Test
    public void editPersonDescriptor_nullObject_failure() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);

        assertNotEquals(descriptor, null);
    }

    @Test
    public void editPersonDescriptor_differentObject_failure() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);

        assertNotEquals(descriptor, "");
    }

    @Test
    public void editPersonDescriptor_differentVariables_failure() {
        Optional<List<String>> tagsOne = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tagsOne);

        Optional<List<String>> tagsTwo = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND));
        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_TWO), Optional.of(EditCommandTestUtil.VALID_PHONE_ONE),
                Optional.empty(), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE), tagsTwo);

        assertNotEquals(descriptorOne, descriptorTwo);
    }

    @Test
    public void editPersonDescriptor_differentName_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_TWO), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty());

        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        assertNotEquals(descriptorOne, descriptorTwo);
    }

    @Test
    public void editPersonDescriptor_differentPhone_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(EditCommandTestUtil.VALID_PHONE_ONE), Optional.empty(), Optional.empty(), Optional.empty());

        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(EditCommandTestUtil.VALID_PHONE_TWO), Optional.empty(), Optional.empty(), Optional.empty());

        assertNotEquals(descriptorOne, descriptorTwo);
    }

    @Test
    public void editPersonDescriptor_differentEmail_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.empty(), Optional.empty());

        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.of(EditCommandTestUtil.VALID_EMAIL_TWO), Optional.empty(), Optional.empty());

        assertNotEquals(descriptorOne, descriptorTwo);
    }

    @Test
    public void editPersonDescriptor_differentAddress_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                Optional.empty());

        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.of(EditCommandTestUtil.VALID_ADDRESS_TWO),
                Optional.empty());

        assertNotEquals(descriptorOne, descriptorTwo);
    }

    @Test
    public void editPersonDescriptor_differentTags_failure() {
        Optional<List<String>> tagsOne = Optional.of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND));
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), tagsOne);

        Optional<List<String>> tagsTwo = Optional.of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), tagsTwo);

        assertNotEquals(descriptorOne, descriptorTwo);
    }
}

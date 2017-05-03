package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditCommandTestUtil;

public class EditCommandTest {
    @Test
    public void equals_sameVariables_success() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);
        EditCommand commandOne = new EditCommand(1, descriptorOne);

        EditPersonDescriptor descriptorTwo = new EditPersonDescriptor(descriptorOne);
        EditCommand commandTwo = new EditCommand(1, descriptorTwo);

        assertTrue(commandOne.equals(commandTwo));
    }

    @Test
    public void equals_sameObject_success() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);
        EditCommand command = new EditCommand(1, descriptor);

        assertTrue(command.equals(command));
    }

    @Test
    public void equals_nullObject_failure() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);
        EditCommand command = new EditCommand(1, descriptor);

        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentObject_failure() {
        Optional<List<String>> tags = Optional
                .of(Arrays.asList(EditCommandTestUtil.VALID_TAG_HUSBAND, EditCommandTestUtil.VALID_TAG_HUBBY));
        EditPersonDescriptor descriptor = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_ONE), Optional.of(EditCommandTestUtil.VALID_PHONE_TWO),
                Optional.of(EditCommandTestUtil.VALID_EMAIL_ONE), Optional.of(EditCommandTestUtil.VALID_ADDRESS_ONE),
                tags);
        EditCommand command = new EditCommand(1, descriptor);

        assertFalse(command.equals(new ClearCommand()));
    }

    @Test
    public void equals_differentVariables_failure() {
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

        assertFalse(commandOne.equals(commandTwo));
    }

    @Test
    public void equals_differentIndex_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(
                Optional.of(EditCommandTestUtil.VALID_NAME_TWO), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty());
        EditCommand commandOne = new EditCommand(1, descriptorOne);
        EditCommand commandTwo = new EditCommand(2, descriptorOne);

        assertFalse(commandOne.equals(commandTwo));
    }

    @Test
    public void equals_differentDescriptor_failure() {
        EditPersonDescriptor descriptorOne = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(EditCommandTestUtil.VALID_PHONE_ONE), Optional.empty(), Optional.empty(), Optional.empty());
        EditCommand commandOne = new EditCommand(1, descriptorOne);

        EditPersonDescriptor descriptorTwo = EditCommandTestUtil.createEditPersonDescriptor(Optional.empty(),
                Optional.of(EditCommandTestUtil.VALID_PHONE_TWO), Optional.empty(), Optional.empty(), Optional.empty());
        EditCommand commandTwo = new EditCommand(1, descriptorTwo);

        assertFalse(commandOne.equals(commandTwo));
    }
}

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditCommandTestUtil;

public class EditCommandTest {
    private static final EditCommand standardCommand = new EditCommand(1, EditCommandTestUtil.standardDescriptorOne);

    @Test
    public void equals_sameValues_success() {
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(EditCommandTestUtil.standardDescriptorOne);
        EditCommand differentCommand = new EditCommand(1, copyDescriptor);

        assertTrue(standardCommand.equals(differentCommand));
    }

    @Test
    public void equals_sameObject_success() {
        assertTrue(standardCommand.equals(standardCommand));
    }

    @Test
    public void equals_nullObject_failure() {
        assertFalse(standardCommand.equals(null));
    }

    @Test
    public void equals_differentObject_failure() {
        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    @Test
    public void equals_differentValues_failure() {
        EditCommand differentCommand = new EditCommand(2, EditCommandTestUtil.standardDescriptorTwo);

        assertFalse(standardCommand.equals(differentCommand));
    }

    @Test
    public void equals_differentIndex_failure() {
        EditCommand differentCommand = new EditCommand(2, EditCommandTestUtil.standardDescriptorOne);

        assertFalse(standardCommand.equals(differentCommand));
    }

    @Test
    public void equals_differentDescriptor_failure() {
        EditCommand differentCommand = new EditCommand(1, EditCommandTestUtil.standardDescriptorTwo);

        assertFalse(standardCommand.equals(differentCommand));
    }
}

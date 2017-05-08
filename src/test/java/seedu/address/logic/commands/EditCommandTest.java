package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EditCommandTestUtil.DESC_AMY;
import static seedu.address.testutil.EditCommandTestUtil.DESC_BOB;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;

public class EditCommandTest {
    private static final EditCommand standardCommand = new EditCommand(1, DESC_AMY);

    @Test
    public void equals_returnsTrue() {
        // same values
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(1, copyDescriptor);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object
        assertTrue(standardCommand.equals(standardCommand));
    }

    @Test
    public void equals_returnsFalse() {
        // null
        assertFalse(standardCommand.equals(null));

        // different type
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index
        assertFalse(standardCommand.equals(new EditCommand(2, DESC_AMY)));

        // different descriptor
        assertFalse(standardCommand.equals(new EditCommand(1, DESC_BOB)));
    }
}

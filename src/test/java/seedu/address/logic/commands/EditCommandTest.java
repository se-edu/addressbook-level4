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
    public void equals() {
        // equals
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(1, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues)); // same values

        assertTrue(standardCommand.equals(standardCommand)); // same object

        // not equals
        assertFalse(standardCommand.equals(null)); // null
        assertFalse(standardCommand.equals(new ClearCommand())); // different type
        assertFalse(standardCommand.equals(new EditCommand(2, DESC_AMY))); // different index
        assertFalse(standardCommand.equals(new EditCommand(1, DESC_BOB))); // different descriptor
    }
}

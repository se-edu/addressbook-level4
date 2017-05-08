package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EditCommandTestUtil.STANDARD_DESCRIPTION_ONE;
import static seedu.address.testutil.EditCommandTestUtil.STANDARD_DESCRIPTION_TWO;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;

public class EditCommandTest {
    private static final EditCommand standardCommand = new EditCommand(1, STANDARD_DESCRIPTION_ONE);

    @Test
    public void equals_sameValues_returnsTrue() {
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(STANDARD_DESCRIPTION_ONE);
        EditCommand commandWithSameValues = new EditCommand(1, copyDescriptor);

        assertTrue(standardCommand.equals(commandWithSameValues));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(standardCommand.equals(standardCommand));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        assertFalse(standardCommand.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        EditCommand differentCommand = new EditCommand(2, STANDARD_DESCRIPTION_TWO);

        assertFalse(standardCommand.equals(differentCommand));
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        EditCommand differentCommand = new EditCommand(2, STANDARD_DESCRIPTION_ONE);

        assertFalse(standardCommand.equals(differentCommand));
    }

    @Test
    public void equals_differentDescriptor_returnsFalse() {
        EditCommand differentCommand = new EditCommand(1, STANDARD_DESCRIPTION_TWO);

        assertFalse(standardCommand.equals(differentCommand));
    }
}

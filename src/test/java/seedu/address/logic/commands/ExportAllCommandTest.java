package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ExportAllCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalFiletypes.FILETYPE_CSV;
import static seedu.address.testutil.TypicalFiletypes.FILETYPE_VCF;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author jitwei98
/**
 * Contains integration tests (interaction with the Model) and unit tests for ExportAllCommand.
 */
public class ExportAllCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandFailure(new ExportAllCommand(FILETYPE_CSV), model, new CommandHistory(),
                String.format(MESSAGE_ARGUMENTS, FILETYPE_CSV));
    }

    @Test
    public void equals() {
        final ExportAllCommand standardCommand = new ExportAllCommand(FILETYPE_CSV);

        // same value -> returns true
        ExportAllCommand commandWithSameArgument = new ExportAllCommand(FILETYPE_CSV);
        assertTrue(standardCommand.equals(commandWithSameArgument));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null value -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListCommand()));

        // different filetype -> returns false
        assertFalse(standardCommand.equals(new ExportAllCommand(FILETYPE_VCF)));
    }
}

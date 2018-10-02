package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ExportAllCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
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
        assertCommandFailure(new ExportAllCommand(), model, new CommandHistory(), MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test; //import org.junit.Before;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ScheduleCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();


    @Test
    public void execute() {
        // unable to add schedule in model
        assertCommandFailure(new ScheduleCommand(), model, commandHistory, ScheduleCommand.MESSAGE_FAILURE);
    }
}

package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalContactList;
import static seedu.address.testutil.TypicalPurchases.getTypicalExpenditureList;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for SortTaskCommand.
 */
public class SortTaskCommandTest {
    private Model model = new ModelManager(getTypicalContactList(), new UserPrefs(),
            getTypicalTaskList(), getTypicalExpenditureList(), getTypicalWorkoutList());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        String expectedMessage = SortTaskCommand.MESSAGE_SUCCESS;
        ModelManager expectedModel = new ModelManager(model.getContactList(), new UserPrefs(),
                model.getTaskList(), model.getExpenditureList(), model.getWorkoutList());
        expectedModel.sortTask();
        expectedModel.commitTaskList();
        assertCommandSuccess(new SortTaskCommand(), model, commandHistory, expectedMessage, expectedModel);
    }
}

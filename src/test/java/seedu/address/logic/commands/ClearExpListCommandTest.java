package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalContactList;
import static seedu.address.testutil.TypicalPurchases.getTypicalExpenditureList;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ExpenditureList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearExpListCommandTest {
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyExpenditureList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitExpenditureList();

        assertCommandSuccess(new ClearExpListCommand(), model, commandHistory, ClearExpListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyExpenditureList_success() {
        Model model = new ModelManager(getTypicalContactList(), new UserPrefs(),
                getTypicalTaskList(), getTypicalExpenditureList(), getTypicalWorkoutList());
        Model expectedModel = new ModelManager(getTypicalContactList(), new UserPrefs(),
                getTypicalTaskList(), getTypicalExpenditureList(), getTypicalWorkoutList());
        expectedModel.setExpenditureList(new ExpenditureList());
        expectedModel.commitExpenditureList();

        assertCommandSuccess(new ClearExpListCommand(), model, commandHistory, ClearExpListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalContactList;
import static seedu.address.testutil.TypicalPurchases.getTypicalExpenditureList;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

/**
 * Unit tests for {@code TickTaskCommand}.
 */
public class TickTaskCommandTest {

    private Model model = new ModelManager(getTypicalContactList(), new UserPrefs(),
            getTypicalTaskList(), getTypicalExpenditureList(), getTypicalWorkoutList());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToTick = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        TickTaskCommand tickTaskCommand = new TickTaskCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(TickTaskCommand.MESSAGE_TICK_TASK_SUCCESS, taskToTick);

        ModelManager expectedModel = new ModelManager(model.getContactList(), new UserPrefs(),
                model.getTaskList(), model.getExpenditureList(), model.getWorkoutList());
        expectedModel.addTickedTaskList(taskToTick);
        expectedModel.deleteTask(taskToTick);
        expectedModel.commitTickedTaskList();
        expectedModel.commitTaskList();

        assertCommandSuccess(tickTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        TickTaskCommand tickTaskCommand = new TickTaskCommand(outOfBoundIndex);

        assertCommandFailure(tickTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {

        Task taskToTick = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        TickTaskCommand tickTaskCommand = new TickTaskCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(TickTaskCommand.MESSAGE_TICK_TASK_SUCCESS, taskToTick);

        Model expectedModel = new ModelManager(model.getContactList(), new UserPrefs(),
                model.getTaskList(), model.getExpenditureList(), model.getWorkoutList());
        expectedModel.addTickedTaskList(taskToTick);
        expectedModel.deleteTask(taskToTick);
        expectedModel.commitTickedTaskList();
        expectedModel.commitTaskList();
        showNoTask(expectedModel);

        assertCommandSuccess(tickTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void equals() {
        TickTaskCommand tickFirstCommand = new TickTaskCommand(INDEX_FIRST_TASK);
        TickTaskCommand tickSecondCommand = new TickTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(tickFirstCommand.equals(tickFirstCommand));

        // same values -> returns true
        TickTaskCommand tickFirstCommandCopy = new TickTaskCommand(INDEX_FIRST_TASK);
        assertTrue(tickFirstCommand.equals(tickFirstCommandCopy));

        // different types -> returns false
        assertFalse(tickFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tickFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(tickFirstCommand.equals(tickSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }

}

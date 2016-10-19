package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Toggles the Task status from incomplete to complete and vice-versa
 *
 */
public class DoneCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the status of the selected task between completed and incomplete.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Task %1s status has been changed to %1s.";

    public final int targetIndex;
    private boolean isExecutedBefore;
    private ReadOnlyTask taskToMark;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        isExecutedBefore = false;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        taskToMark = lastShownList.get(targetIndex - 1);
        try {
            model.markTask(taskToMark);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target cannot be missing.";
        }
        isExecutedBefore = pushCmdToUndo(isExecutedBefore);
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToMark.getName(),
                taskToMark.getCompleted() ? "Completed" : "Incomplete"));
    }

    @Override
    public CommandResult unexecute() {
        assert model != null;
        assert undoRedoManager != null;

        try {
            model.markTask(taskToMark);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target cannot be missing.";
        }
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToMark.getName(),
                taskToMark.getCompleted() ? "Completed" : "Incomplete"));
    }

    @Override
    public boolean pushCmdToUndo(boolean isExecuted) {
        if (!isExecuted){
            undoRedoManager.addToUndo(this);
        }
        return true;
    }

}

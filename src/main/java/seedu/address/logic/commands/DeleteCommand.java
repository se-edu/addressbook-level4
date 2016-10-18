package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Location;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.Time;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the SmartyDo.
 */
public class DeleteCommand extends Command implements Undoable{

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the SmartyDo";

    public final int targetIndex;
    private ReadOnlyTask taskToDelete;
    private boolean isExecutedBefore;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        taskToDelete = null;
        isExecutedBefore = false;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
            isExecutedBefore = pushCmdToUndo(isExecutedBefore);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Override
    public CommandResult unexecute() {
    	 assert model != null;
         assert undoRedoManager != null;
         assert taskToDelete != null;

         try {
             model.addTask((Task) taskToDelete);
             return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
         } catch (UniqueTaskList.DuplicateTaskException e) {
             assert false: "impossible for person to be missing";
             return new CommandResult(MESSAGE_DUPLICATE_TASK);
         }
    }

	@Override
	public boolean pushCmdToUndo(boolean isExecuted) {
        if (!isExecuted){
        	undoRedoManager.addToUndo(this);
        }
		return true;
	}
}

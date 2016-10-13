package seedu.todoList.logic.commands;

import seedu.todoList.commons.core.Messages;
import seedu.todoList.commons.core.UnmodifiableObservableList;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the TodoList.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the task type and the index number used in the last task listing.\n"
            + "Parameters: TASK_TYPE INDEX_NUMBER(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " todo 1\n"
            + "Example: " + COMMAND_WORD + " event 1\n"
            + "Example: " + COMMAND_WORD + " deadline 1";

    public static final String MESSAGE_DELETE_task_SUCCESS = "Deleted task: %1$s";

    public final String dataType;
    public final int targetIndex;

    public DeleteCommand(String dataType, int targetIndex) {
    	this.dataType = dataType;
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {
    	
    	UnmodifiableObservableList<ReadOnlyTask> lastShownList = null;
    	switch (dataType) {
    		case "todo":
    			lastShownList = model.getFilteredTodoList();
    			break;
    		case "event":
    			lastShownList = model.getFilteredEventList();
    			break;
    		case "deadline":
    			lastShownList = model.getFilteredDeadlineList();
    	}
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_task_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete, dataType);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_task_SUCCESS, taskToDelete));
    }

}

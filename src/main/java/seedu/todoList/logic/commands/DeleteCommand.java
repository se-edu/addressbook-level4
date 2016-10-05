package seedu.todoList.logic.commands;

import seedu.todoList.commons.core.Messages;
import seedu.todoList.commons.core.UnmodifiableObservableList;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.UniqueTaskList.taskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the Todo book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_task_SUCCESS = "Deleted task: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_task_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (taskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_task_SUCCESS, taskToDelete));
    }

}

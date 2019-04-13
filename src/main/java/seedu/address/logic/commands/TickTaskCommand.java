package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Ticks the task and adds them into the tickedTaskList
 */
public class TickTaskCommand extends Command {

    public static final String COMMAND_WORD = "ticktask";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Ticks a task identified by the index number used in the displayed task list. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_TICK_TASK_SUCCESS = "Ticked task: %1$s";

    private final Index targetIndex;

    public TickTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToTick = lastShownList.get(targetIndex.getZeroBased());
        model.addTickedTaskList(taskToTick);
        model.deleteTask(taskToTick);
        model.commitTickedTaskList();
        model.commitTaskList();
        return new CommandResult(String.format(MESSAGE_TICK_TASK_SUCCESS, taskToTick));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TickTaskCommand // instanceof handles nulls
                && targetIndex.equals(((TickTaskCommand) other).targetIndex)); // state check
    }

}

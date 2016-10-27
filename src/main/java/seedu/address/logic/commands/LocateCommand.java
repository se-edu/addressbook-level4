package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.LocateItemRequestEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.commons.core.UnmodifiableObservableList;

/**
 * Selects a task identified using it's last displayed index from the SmartyDo.
 */
public class LocateCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "locate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Peform an online search of the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_TASK_SUCCESS = "Searching For Task: %1$s";

    public LocateCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        
        ReadOnlyTask taskToShow = lastShownList.get(targetIndex - 1);

        EventsCenter.getInstance().post(new LocateItemRequestEvent(taskToShow, targetIndex -1));
        return new CommandResult(String.format(MESSAGE_LOCATE_TASK_SUCCESS, targetIndex));

    }
}

package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0135767U
/**
 * Lists all tasks in the SmartyDo to the user.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows details of a specified task.\n"
            + "Parameters: INDEX \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Selected Task #%1$s";

    public final int targetIndex;
    
    public SelectCommand(int targetIndex) {
    	this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex));
        
    }
}

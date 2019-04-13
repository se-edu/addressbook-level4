package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts the tasks in the task list according to their deadline time and date.
 */
public class SortTaskCommand extends Command {

    public static final String COMMAND_WORD = "sorttask";
    public static final String MESSAGE_SUCCESS = "task List has been sorted";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.sortTask();
        model.commitTaskList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

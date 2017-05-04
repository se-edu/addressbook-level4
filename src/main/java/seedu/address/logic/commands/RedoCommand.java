package seedu.address.logic.commands;

import static com.google.common.base.Preconditions.checkNotNull;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Redo the previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "There's no more commands left to be redone!";

    @Override
    public CommandResult execute() throws CommandException {
        checkNotNull(model);
        checkNotNull(history);

        history.executeNextReversibleCommand();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}

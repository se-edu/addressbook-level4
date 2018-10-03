package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Show CAP based on existing modules.
 */
public class CapCommand extends Command {
    public static final String COMMAND_WORD = "cap";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculate current CAP with given modules "
            + "Parameters: NONE "
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Your Current CAP is: %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        double cap = model.getCap();
        return new CommandResult(String.format(MESSAGE_SUCCESS, cap));
    }
}

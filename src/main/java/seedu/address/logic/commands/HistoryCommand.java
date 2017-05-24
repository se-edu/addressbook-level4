package seedu.address.logic.commands;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_SUCCESS = "Entered commands (from earliest to most recent):\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";

    @Override
    public CommandResult execute() {
        // exclude the last executed command
        List<String> previousCommands = history.getHistory();
        previousCommands.remove(previousCommands.size() - 1);

        return previousCommands.isEmpty() ? new CommandResult(MESSAGE_NO_HISTORY)
                : new CommandResult(String.format(MESSAGE_SUCCESS,
                previousCommands.stream().collect(Collectors.joining("\n"))));
    }

}

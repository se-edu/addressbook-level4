package seedu.address.logic.commands;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.History;
import seedu.address.model.Model;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_SUCCESS = "Entered commands (from earliest to most recent):\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";

    @Override
    public CommandResult execute() {
        List<String> previousCommands = history.getHistory();

        return previousCommands.isEmpty() ? new CommandResult(MESSAGE_NO_HISTORY)
                : new CommandResult(String.format(MESSAGE_SUCCESS,
                previousCommands.stream().collect(Collectors.joining("\n"))));
    }

    @Override
    public void setData(Model model, History history) {
        this.model = model;
        this.history = history;
    }
}

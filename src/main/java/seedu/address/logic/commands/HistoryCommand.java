package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandObject;
import seedu.address.model.Model;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_SUCCESS = "Entered commands (from most recent to earliest):\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";

    @Override
    public CommandResult execute() {
        List<CommandObject> previousCommandsObjects = history.getHistory();

        if (previousCommandsObjects.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        List<String> previousUserInput = previousCommandsObjects.stream()
                .map(commandObject -> commandObject.userInput).collect(Collectors.toList());

        Collections.reverse(previousUserInput);
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousUserInput)));
    }

    @Override
    public void setData(Model model, CommandHistory history) {
        requireNonNull(history);
        this.history = history;
    }
}

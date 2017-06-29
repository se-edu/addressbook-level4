package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.HistoryIterator;
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
        HistoryIterator previousCommands = history.getHistory();

        if (!previousCommands.hasCurrent()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                String.join("\n", getReversedCommands(previousCommands))));
    }

    @Override
    public void setData(Model model, CommandHistory history) {
        requireNonNull(history);
        this.history = history;
    }

    private List<String> getReversedCommands(HistoryIterator iterator) {
        List<String> list = new ArrayList<>();
        list.add(iterator.current());

        while (iterator.hasPrevious()) {
            list.add(iterator.previous());
        }
        Collections.reverse(list);

        return list;
    }
}

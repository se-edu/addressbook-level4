package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private LinkedList<CommandObject> commandObjectHistory;

    public CommandHistory() {
        commandObjectHistory = new LinkedList<>();
    }

    /**
     * Appends {@code commandObject} to the list of user input entered.
     */
    public void add(CommandObject commandObject) {
        requireNonNull(commandObject);
        commandObjectHistory.add(commandObject);
    }

    /**
     * Returns a defensive copy of {@code commandObjectHistory}.
     */
    public List<CommandObject> getHistory() {
        return new LinkedList<>(commandObjectHistory);
    }
}

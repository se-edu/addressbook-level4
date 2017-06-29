package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

/**
 * Stores the history of commands executed. Elements added into this object are always prepended to simulate the
 * reverse-chronological order of history.
 */
public class CommandHistory {
    private ArrayList<String> userInputHistory;

    public CommandHistory() {
        userInputHistory = new ArrayList<>();
    }

    /**
     * Prepends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(0, userInput);
    }

    /**
     * Returns a defensive copy of {@code userInputHistory} encapsulated in a {@code HistorySnapshot} object.
     */
    public HistorySnapshot getHistory() {
        return new HistorySnapshot(userInputHistory);
    }
}

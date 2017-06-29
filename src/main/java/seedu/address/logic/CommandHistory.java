package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

/**
 * Stores the history of commands executed. Elements added into this object is always prepended to simulate the
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
    public void addFirst(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(0, userInput);
    }

    /**
     * Returns a defensive copy of {@code userInputHistory} as a {@code HistoryIterator}.
     */
    public HistoryIterator<String> getHistory() {
        return new HistoryIterator<>(userInputHistory);
    }
}

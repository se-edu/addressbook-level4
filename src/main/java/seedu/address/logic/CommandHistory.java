package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private ArrayList<String> userInputHistory;

    public CommandHistory() {
        userInputHistory = new ArrayList<>();
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<String> getHistory() {
        return new ArrayList<>(userInputHistory);
    }
}

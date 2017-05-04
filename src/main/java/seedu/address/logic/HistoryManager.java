package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the history of commands executed.
 */
public class HistoryManager {
    private ArrayList<String> userInputHistory;

    public HistoryManager() {
        userInputHistory = new ArrayList<>();
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        userInputHistory.add(userInput);
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<String> getHistory() {
        return new ArrayList<>(userInputHistory);
    }
}

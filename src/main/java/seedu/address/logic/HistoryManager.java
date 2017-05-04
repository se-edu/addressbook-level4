package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the history of commands executed.
 */
public class HistoryManager implements History {
    private ArrayList<String> userInputHistory;

    public HistoryManager() {
        userInputHistory = new ArrayList<>();
    }

    @Override
    public void add(String userInput) {
        userInputHistory.add(userInput);
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    @Override
    public List<String> getHistory() {
        return new ArrayList<>(userInputHistory);
    }
}

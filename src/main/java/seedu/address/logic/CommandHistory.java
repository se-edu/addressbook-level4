package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
// new comment about iterator
/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private ArrayList<String> userInputHistory;
    private int currIndex;

    public CommandHistory() {
        userInputHistory = new ArrayList<>();
        currIndex = 0;
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
        currIndex++;
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<String> getHistory() {
        return new ArrayList<>(userInputHistory);
    }

    public String previous() {
        currIndex--;
        if (currIndex == -1) {
            currIndex = 0;
        }
        return userInputHistory.get(currIndex);
    }

    public String next() {
        currIndex++;
        if (currIndex == userInputHistory.size()) {
            currIndex = userInputHistory.size() - 1;
            return "";
        }
        return userInputHistory.get(currIndex);
    }

}

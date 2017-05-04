package seedu.address.logic;

import java.util.List;

/**
 * Stores the history of commands executed.
 */
public interface History {
    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput);

    /**
     * Returns the list of user input entered.
     */
    public List<String> getHistory();
}

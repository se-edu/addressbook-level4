package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private final ObservableList<String> userInputHistory = FXCollections.observableArrayList();
    private final ObservableList<String> unmodifiableUserInputHistory =
            FXCollections.unmodifiableObservableList(userInputHistory);

    public CommandHistory() {}

    public CommandHistory(CommandHistory commandHistory) {
        userInputHistory.addAll(commandHistory.userInputHistory);
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
    }

    /**
     * Returns an unmodifiable view of {@code userInputHistory}.
     */
    public ObservableList<String> getHistory() {
        return unmodifiableUserInputHistory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandHistory)) {
            return false;
        }

        CommandHistory otherCommandHistory = (CommandHistory) other;
        return userInputHistory.equals(otherCommandHistory.userInputHistory);
    }

    @Override
    public int hashCode() {
        return userInputHistory.hashCode();
    }
}

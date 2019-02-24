package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private final ObservableList<String> history = FXCollections.observableArrayList();
    private final ObservableList<String> unmodifiableHistory = FXCollections.unmodifiableObservableList(history);

    public CommandHistory() {}

    public CommandHistory(CommandHistory commandHistory) {
        history.addAll(commandHistory.history);
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        history.add(userInput);
    }

    /**
     * Returns an unmodifiable view of {@code history}.
     */
    public ObservableList<String> getHistory() {
        return unmodifiableHistory;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        // state check
        CommandHistory other = (CommandHistory) obj;
        return history.equals(other.history);
    }

    @Override
    public int hashCode() {
        return history.hashCode();
    }
}

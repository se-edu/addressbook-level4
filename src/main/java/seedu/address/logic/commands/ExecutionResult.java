package seedu.address.logic.commands;

/**
 * Represents the result of a command execution.
 */
public class ExecutionResult {
    public final String feedbackToUser;
    public final String warning;

    public ExecutionResult(String feedbackToUser, String warning) {
        this.feedbackToUser = feedbackToUser;
        this.warning = warning;
    }
}

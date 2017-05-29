package seedu.address.logic.commands;

/**
 * Represents the result of the entire execution process, including the parsing of input and execution of command.
 */
public class ExecutionResult {
    public final String feedbackToUser;
    public final String warning;

    public ExecutionResult(String feedbackToUser, String warning) {
        this.feedbackToUser = feedbackToUser;
        this.warning = warning;
    }
}

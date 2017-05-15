package seedu.address.logic.commands;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        checkNotNull(feedbackToUser);
        this.feedbackToUser = feedbackToUser;
    }

}

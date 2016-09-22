package seedu.address.logic.commands;

/**
 * Represents a result of an incorrect command.
 */
public class IncorrectCommandResult extends CommandResult{

    public IncorrectCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }
}

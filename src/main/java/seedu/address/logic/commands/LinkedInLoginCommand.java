package seedu.address.logic.commands;

/**
 * Allows a user to login to their LinkedIn account
 */
import seedu.address.logic.commands.exceptions.CommandException;
public class LinkedInLoginCommand extends UndoableCommand{
    public static final String COMMAND_WORD = "linkedin_login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in to your LinkedIn account ";
    //TODO: Add username and password as parameter

    public static final String MESSAGE_SUCCESS = "Successfully logged in";

    /**
     * Creates a LinkedInLogin log a Salesperson in to LinkedIn
     */
    public LinkedInLoginCommand(String username, String password) {

    }

    public static final String MESSAGE_FAILED_LOGIN= "Unable to login, please try again";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            //TODO: login to linkedIn via API
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILED_LOGIN);
        }
    }
}

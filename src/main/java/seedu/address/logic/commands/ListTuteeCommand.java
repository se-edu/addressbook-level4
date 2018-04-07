package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTEES;

//@@author yungyung04
/**
 * Lists all tutees in the application to the user.
 */
public class ListTuteeCommand extends Command {

    public static final String COMMAND_WORD = "listtutee";

    public static final String MESSAGE_SUCCESS = "Listed all tutees";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_TUTEES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

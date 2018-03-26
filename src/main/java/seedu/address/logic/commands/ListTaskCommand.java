package seedu.address.logic.commands;

/**
 * Lists all tasks in the application to the user.
 */

public class ListTaskCommand extends Command{

    public static final String COMMAND_WORD = "listTask";
    public static final String COMMAND_ALIAS = "lt";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
       // model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}

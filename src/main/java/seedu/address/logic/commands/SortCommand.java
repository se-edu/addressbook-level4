package seedu.address.logic.commands;

/** By Yang Yuqing
 * Sorts all persons in the addressbook .
 */

public class SortCommand extends Command{


    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    @Override
    public CommandResult execute() {
        model.sortPersons();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}


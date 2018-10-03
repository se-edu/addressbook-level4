package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds the schedule of a person to the address book
 */

public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";
    public static final String COMMAND_ALIAS = "sc";
    public static final String MESSAGE_SUCCESS = "Schedule Added!";
    public static final String MESSAGE_FAILURE = "Unable to add schedule! Exceeded max of 5 events per time slot";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        throw new CommandException(MESSAGE_FAILURE);

        //return new CommandResult(MESSAGE_SUCCESS);

    }
}

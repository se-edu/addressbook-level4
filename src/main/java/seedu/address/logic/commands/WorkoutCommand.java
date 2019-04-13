package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;


/**
 * Finds and lists all persons in contact list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class WorkoutCommand extends Command {

    public static final String COMMAND_WORD = "workout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displaying the 5 most recent workout";

    public static final String MESSAGE_SUCCESS = "Listed recent workouts";


    public WorkoutCommand () {}

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PURCHASES;

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
      //  model.updateFilteredWorkoutList(PREDICATE_SHOW_ALL_PURCHASES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

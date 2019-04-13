package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.workout.Workout;


/**
 * Finds and lists all persons in contact list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class WorkoutCommand extends Command {

    public static final String COMMAND_WORD = "workout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displaying up to 5 most recent workout";

    public static final String MESSAGE_SUCCESS = "Recent workout(s) found!";


    public WorkoutCommand () {}

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        ArrayList<Workout> results;
        requireNonNull(model);
        results = model.getRecent();
        final StringBuilder builder = new StringBuilder();
        int howMany = results.size();
        builder.append(howMany + " most recent workout(s): ").append("\n");
        while (!results.isEmpty()) {
            builder.append(results.get(0).toString());
            results.remove(0);
            builder.append("\n");
        }



        return new CommandResult(MESSAGE_SUCCESS + "\n" + builder.toString());
    }
}

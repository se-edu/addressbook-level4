package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import seedu.address.model.workout.Workout;


public class RecordCommand extends Command {
    public static final String COMMAND_WORD = "record";

    public static final String MESSAGE_SUCCESS = "New workout added: ";

    public static final String MESSAGE_USAGE = "dummy string";

    private final Workout newWorkout;

    /**
     *
     * @param workout that is being added.
     */
    public RecordCommand(Workout workout) {
        requireNonNull(workout);
        newWorkout = workout;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.addWorkout(newWorkout);
        model.commitWorkoutBook();
        String toBePrinted = MESSAGE_SUCCESS  + newWorkout.getExercise() + " | "
                + "SETS: " + newWorkout.getSets() + ' ' + newWorkout.getReps() + "REPS"
                + "TIME: " + newWorkout.getTime();
        return new CommandResult(String.format(toBePrinted, newWorkout));
    }
}

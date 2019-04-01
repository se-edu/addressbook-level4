package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.habit.Habit;

/**
 * Adds a habit to the habit tracker list.
 */
public class AddHabitCommand extends Command {
    public static final String COMMAND_WORD = "addhabit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a habit to the habit tracker list. "
            + "Parameters: "
            + PREFIX_HABITTITLE + "NAME "
            + PREFIX_PROGRESS+ "PROGRESS "
            + "[" + PREFIX_TAG + "TAG]...\n";

    public static final String MESSAGE_SUCCESS = "New habit added: %1s";

    private final Habit toAddHabit;

    /**
     * Creates an AddHabitCommand to add the specified {@code Habit}
     */
    public AddHabitCommand(Habit habit) {
        requireNonNull(habit);
        toAddHabit = habit;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);


        model.addHabit(toAddHabit);
        model.commitHabitTrackerList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddHabit));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddHabitCommand // instanceof handles nulls
                && toAddHabit.equals(((AddHabitCommand) other).toAddHabit));
    }
}

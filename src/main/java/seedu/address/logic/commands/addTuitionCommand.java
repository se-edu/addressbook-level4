package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Task;
import seedu.address.model.person.exceptions.TimingClashException;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Adds a tuition (task) into the schedule.
 */
public class AddTuitionCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addTuition";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tuition (task) into the schedule. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_START_TIME + "START TIME "
            + PREFIX_DURATION + "DURATION "
            + PREFIX_REMARK + "REMARK";

    public static final String MESSAGE_SUCCESS = "New schedule added.";
    private static final String MESSAGE_TASK_TIMING_CLASHES = "This task clashes with another task";

    private final Task toAdd;
    //private final Task newSchedule;

    /**
     * Creates an AddTuition to add the specified {@code Task}
     */
    public AddTuition(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(schedule);
        try {
            schedule.addTask(TASK);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (TimingClashException tce) {
            throw new CommandException(MESSAGE_TASK_TIMING_CLASHES);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTuitionCommand // instanceof handles nulls
                && toAdd.equals(((AddTuitionCommand) other).toAdd));
    }
}

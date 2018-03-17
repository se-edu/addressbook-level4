package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.personal.PersonalSchedule;
import seedu.address.model.personal.PersonalTask;

/**
 * Adds a personal task into the schedule.
 */
public class AddPersonalTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addTask";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a personal task into the schedule.\n"
            + "Parameters: "
            + "Date(dd/mm/yyyy) "
            + "Start time(hh:mm) "
            + "Duration(XXhXXm) "
            + "Description.\n"
            + "Example: " + COMMAND_WORD + " "
            + "10/12/2018 "
            + "12:30 "
            + "1h30m "
            + "Calculus homework page 24.";
    public static final String MESSAGE_SUCCESS = "New personal task added.";

    private final PersonalTask toAdd;

    /**
     * Creates an AddPersonalTaskCommand to add the specified {@code Task}.
     */
    public AddPersonalTaskCommand(PersonalTask task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        new PersonalSchedule().addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPersonalTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddPersonalTaskCommand) other).toAdd));
    }
}

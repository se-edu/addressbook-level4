package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_TASK_TIMING_CLASHES;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.TimingClashException;
import seedu.address.model.tutee.TuitionTask;
import seedu.address.model.tutee.Tutee;

//@@author yungyung04

/**
 * Adds a tuition (task) into the schedule.
 */
public class AddTuitionTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtuition";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tuition (task) into the schedule.\n"
            + "Parameters: "
            + "tutee_index"
            + "Date(dd/mm/yyyy) "
            + "Start time(hh:mm) "
            + "Duration(XXhXXm) "
            + "Description( anything; leading and trailing whitepsaces will be trimmed )\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + "10/12/2018 "
            + "12:30 "
            + "1h30m "
            + "Calculus homework page 24";

    public static final String MESSAGE_SUCCESS = "New tuition task added.";

    private final Index targetIndex;
    private final LocalDateTime taskdateTime;
    private final String duration;
    private final String description;

    private TuitionTask toAdd;
    //private Tutee associatedTutee;
    private String associatedTutee;

    /**
     * Creates an AddTuition to add the specified {@code Task} which is associated to {@code Tutee}.
     */
    public AddTuitionTaskCommand(Index targetIndex, LocalDateTime taskDateTime, String duration, String description) {
        requireNonNull(taskDateTime);
        requireNonNull(duration);
        requireNonNull(description);
        this.taskdateTime = taskDateTime;
        this.duration = duration;
        this.description = description;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.addTask(toAdd);
        } catch (TimingClashException e) {
            throw new CommandException(MESSAGE_TASK_TIMING_CLASHES);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        associatedTutee = getAssociatedTutee().getName().fullName;
        //associatedTutee = getAssociatedTutee();
        //requireNonNull(associatedTutee.getTuitionSchedule());
        //tuitionSchedule = associatedTutee.getTuitionSchedule();
        toAdd = new TuitionTask(associatedTutee, taskdateTime, duration, description);
    }

    /**
     * Returns the {@code Tutee} object that is pointed by the index as shown in the last displayed conatct list.
     */
    private Tutee getAssociatedTutee() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person associatedPerson = lastShownList.get(targetIndex.getZeroBased());
        if (!(associatedPerson instanceof Tutee)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTEE_INDEX);
        }
        return (Tutee) lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTuitionTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTuitionTaskCommand) other).toAdd));
    }
}

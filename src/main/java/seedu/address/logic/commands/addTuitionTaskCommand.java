package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Schedule;
import seedu.address.model.person.Person;
import seedu.address.model.person.TuitionSchedule;
import seedu.address.model.person.TuitionTask;
import seedu.address.model.person.exceptions.TimingClashException;

import seedu.address.logic.commands.exceptions.CommandException;

import java.util.List;

/**
 * Adds a tuition (task) into the schedule.
 */
public class AddTuitionTaskCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addTuition";

    private static final String INDEX_TARGET_PERSON =
            "last shown index of person that is associated with the task (space)";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tuition (task) into the schedule. "
            + "Parameters: "
            + INDEX_TARGET_PERSON
            + "DATE(dd/mm/yyyy) " + "(space) "
            + "START TIME(hh:mm) " + "(space) "
            + "DURATION(HhMm) " + "(space) "
            + "REMARK";

    public static final String MESSAGE_SUCCESS = "New schedule added.";
    private static final String MESSAGE_TASK_TIMING_CLASHES = "This task clashes with another task";

    private final TuitionTask toAdd;
    private final Index targetIndex;

    private Schedule tuitionSchedule = null; // will be assigned
    private Person associatedPerson;

    /**
     * Creates an AddTuition to add the specified {@code Task} which is associated to {@code Person}.
     */
    public AddTuitionTaskCommand(TuitionTask task, Index indexOfAssociatedPerson) {
        requireNonNull(task);
        toAdd = task;
        targetIndex = indexOfAssociatedPerson;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(tuitionSchedule);
        tuitionSchedule.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    //Question : does this agree with SLAB?
    protected void preprocessUndoableCommand() throws CommandException {
        findAssociatedPerson();
        assert(associatedPerson.getSchedule() != null);
        tuitionSchedule = associatedPerson.getSchedule();
    }

    /**
     * Locates {@code Person} that will be associated with the tuition task.
     *
     * @throws commandException if invalid person index was given.
     */
    private void findAssociatedPerson() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        associatedPerson = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTuitionTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTuitionTaskCommand) other).toAdd));
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.Person;
import seedu.address.model.tutee.TuitionSchedule;
import seedu.address.model.tutee.TuitionTask;
import seedu.address.model.tutee.Tutee;

/**
 * Adds a tuition (task) into the schedule.
 */
public class DeleteTuitionTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteTuition";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a tuition (task) from the schedule.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) of Tutee \n"
            + "INDEX (must be a positive integer) of Task"
            + "Example: " + COMMAND_WORD + " 1" + " 1";

    public static final String MESSAGE_SUCCESS = "Tuition task deleted.";

    private final Index targetIndex;
    private final int taskIndex;
    private TuitionTask tuitionTaskToDelete;
    private TuitionSchedule tuitionSchedule;
    private Tutee associatedPerson;

    /**
     * Deletes a TuitionTask {@code Task} which is associated to {@code Person}.
     */
    public DeleteTuitionTaskCommand(Index indexOfAssociatedPerson, int indexOfTuitionTask) {
        targetIndex = indexOfAssociatedPerson;
        taskIndex = indexOfTuitionTask;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(tuitionSchedule);
        tuitionTaskToDelete = tuitionSchedule.deleteTask(taskIndex);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tuitionTaskToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        findAssociatedPerson();
        assert(associatedPerson.getTuitionSchedule() != null);
        tuitionSchedule = associatedPerson.getTuitionSchedule();
    }

    /**
     * Locates {@code Person} that will be associated with the tuition task.
     *
     * @throw commandException if invalid person index was given.
     */
    private void findAssociatedPerson() throws CommandException {
        List<Person> lastShownList = model.getSortedAndFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        associatedPerson = (Tutee) lastShownList.get(targetIndex.getZeroBased());
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTuitionTaskCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTuitionTaskCommand) other).targetIndex));
    }
}

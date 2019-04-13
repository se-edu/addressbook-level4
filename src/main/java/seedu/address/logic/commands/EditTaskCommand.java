package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DeadlineDate;
import seedu.address.model.task.DeadlineTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;




/**
 * edits a task
 * takes an index of the task and finds it and edits it
 */
public class EditTaskCommand extends Command {
    public static final String COMMAND_WORD = "edittask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DEADLINE_TIME + "DeadlineTime] "
            + "[" + PREFIX_DEADLINE_DATE + "DeadlineDate] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE_DATE + "311219 "
            + PREFIX_DEADLINE_TIME + "2359";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);
        this.index = index;
        this.editTaskDescriptor = editTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.setTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskList();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        TaskName updatedTaskName = editTaskDescriptor.getTaskName().orElse(taskToEdit.getTaskName());
        DeadlineTime updatedDeadlineTime =
                editTaskDescriptor.getDeadlineTime().orElse(taskToEdit.getDeadlineTime());
        DeadlineDate updatedDeadlineDate =
                editTaskDescriptor.getDeadlineDate().orElse(taskToEdit.getDeadlineDate());
        Set<Tag> updatedTags =
                editTaskDescriptor.getTags().orElse(taskToEdit.getTags());
        return new Task(updatedTaskName, updatedDeadlineTime, updatedDeadlineDate, updatedTags);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }
        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private TaskName taskName;
        private DeadlineTime deadlineTime;
        private DeadlineDate deadlineDate;
        private Set<Tag> tags;

        public EditTaskDescriptor(){}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setTaskName(toCopy.taskName);
            setDeadlineDate(toCopy.deadlineDate);
            setDeadlineTime(toCopy.deadlineTime);
            setTags(toCopy.tags);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(taskName, deadlineDate, deadlineTime, tags);
        }

        public void setTaskName(TaskName taskName) {
            this.taskName = taskName;
        }
        public Optional<TaskName> getTaskName() {
            return Optional.ofNullable(taskName);
        }

        public void setDeadlineDate(DeadlineDate deadlineDate) {
            this.deadlineDate = deadlineDate;
        }
        public Optional<DeadlineDate> getDeadlineDate() {
            return Optional.ofNullable(deadlineDate);
        }

        public void setDeadlineTime(DeadlineTime deadlineTime) {
            this.deadlineTime = deadlineTime;
        }
        public Optional<DeadlineTime> getDeadlineTime() {
            return Optional.ofNullable(deadlineTime);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }
            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }
            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;
            return getTaskName().equals(e.getTaskName())
                    && getDeadlineDate().equals(e.getDeadlineDate())
                    && getDeadlineTime().equals(e.getDeadlineTime())
                    && getTags().equals(e.getTags());
        }
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.task.TaskSortUtil.CATEGORY_DATE_TIME;
import static seedu.address.model.task.TaskSortUtil.CATEGORY_MONTH;

import java.util.Comparator;

import seedu.address.model.Task;
import seedu.address.model.task.TaskSortUtil;

//@@author yungyung04
/**
 * Sorts all tasks from the last shown list according to the specified sorting category in an increasing order
 */
public class SortTaskCommand extends Command {
    public static final String COMMAND_WORD = "sorttaskby";

    public static final String MESSAGE_SUCCESS = "sorted list of tasks successfully";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " * Sorts all tasks from the last shown list according to the specified sorting category in an increasing order\n"
            + "Parameter: sort_category\n"
            + "Choice of sort_categories: "
            + CATEGORY_MONTH + ", "
            + CATEGORY_DATE_TIME + "\n"
            + "Example: " + COMMAND_WORD + " " + CATEGORY_MONTH;

    private final String category;
    private final Comparator<Task> comparator;

    public SortTaskCommand(String category) {
        requireNonNull(category);
        this.category = category;
        comparator = new TaskSortUtil().getComparator(category);
    }

    @Override
    public CommandResult execute() {
        requireNonNull(comparator);
        model.sortFilteredTaskList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortTaskCommand // instanceof handles nulls
                && category.equals(((SortTaskCommand) other).category));
    }
}

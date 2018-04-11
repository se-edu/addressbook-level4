package seedu.address.logic.commands;

import static seedu.address.model.task.TaskSortUtil.CATEGORY_MONTH;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.model.Task;
import seedu.address.model.task.MonthContainsKeywordsPredicate;

//@@author yungyung04
/**
 * Finds and lists all tasks in the task list based on the specified filter category.
 */
public class FindTaskCommand extends Command {
    public static final String COMMAND_WORD = "findtaskby";

    public static final String MESSAGE_SUCCESS = "Find is successful.";

    public static final String INPUT_TYPE_BETWEEN = "between";
    public static final String INPUT_TYPE_NAMELY = "namely";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": lists all tasks that suit the specified category\n"
            + "Parameters: CATEGORY FIND_TYPE KEYWORDS\n"
            + "1st Example: " + COMMAND_WORD + " " + CATEGORY_MONTH + " " + INPUT_TYPE_BETWEEN + " April October\n"
            + "2nd Example: " + COMMAND_WORD + " " + CATEGORY_MONTH + " " + INPUT_TYPE_NAMELY
            + " 2 05 Aug December now";

    private final String category;
    private final String[] keywords;
    private Predicate<Task> taskPredicate;

    public FindTaskCommand(String category, String[] keywords) {
        this.category = category;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        switch (category) {
        case CATEGORY_MONTH:
            taskPredicate = new MonthContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredTaskList(taskPredicate);
            break;
        default:
            // invalid category should be detected in parser instead
            assert (false);
        }
        return new CommandResult(MESSAGE_SUCCESS + "\n"
                + getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTaskCommand // instanceof handles nulls
                && category.equals(((FindTaskCommand) other).category)
                && hasSameValue(keywords, ((FindTaskCommand) other).keywords));
    }

    /**
     * Returns true if both the given arrays of String contain the same elements.
     */
    private boolean hasSameValue(String[] firstKeywords, String[] secondKeywords) {
        if (firstKeywords.length != secondKeywords.length) {
            return false;
        }

        for (int i = 0; i < firstKeywords.length; i++) {
            if (!firstKeywords[i].equals(secondKeywords[i])) {
                return false;
            }
        }
        return true;
    }
}

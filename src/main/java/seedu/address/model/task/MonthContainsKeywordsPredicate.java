package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.Task;

//@@author yungyung04
/**
 * Tests that a {@code Task}'s month matches any of the {@code int month} given.
 */
public class MonthContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public MonthContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        Integer.toString(task.getTaskDateTime().getMonthValue()), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.task.MonthContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords
                .equals(((seedu.address.model.task.MonthContainsKeywordsPredicate) other).keywords)); // state check
    }
}

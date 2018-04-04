package seedu.address.logic.commands;

import static seedu.address.model.person.PersonSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_GRADE;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_MONTH;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_NAME;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SUBJECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.Task;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.task.MonthContainsKeywordsPredicate;
import seedu.address.model.tutee.EducationLevelContainsKeywordsPredicate;
import seedu.address.model.tutee.GradeContainsKeywordsPredicate;
import seedu.address.model.tutee.SchoolContainsKeywordsPredicate;
import seedu.address.model.tutee.SubjectContainsKeywordsPredicate;

//@@author yungyung04
/**
 * Finds and lists either all persons or tasks in address book depending on the specified filter category.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_SUCCESS = "Find is successful.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": lists all person or tasks that suit the specified category\n"
            + "Parameters: filter_category keyword\n"
            + "Choice of filter_categories: "
            + CATEGORY_NAME + ", "
            + CATEGORY_MONTH + ", "
            + CATEGORY_EDUCATION_LEVEL + ", "
            + CATEGORY_GRADE + ", "
            + CATEGORY_SCHOOL + ", "
            + CATEGORY_SUBJECT + "\n"
            + "Example: " + COMMAND_WORD + " " + CATEGORY_GRADE + " A";

    private final String category;
    private final String[] keywords;
    private Predicate<Person> personPredicate;
    private Predicate<Task> taskPredicate;

    public FindCommand(String category, String[] keywords) {
        this.category = category;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        switch (category) {
        case CATEGORY_NAME:
            personPredicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(personPredicate);
            break;
        case CATEGORY_MONTH:
            taskPredicate = new MonthContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredTaskList(taskPredicate);
            break;
        case CATEGORY_EDUCATION_LEVEL:
            personPredicate = new EducationLevelContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(personPredicate);
            break;
        case CATEGORY_GRADE:
            personPredicate = new GradeContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(personPredicate);
            break;
        case CATEGORY_SCHOOL:
            personPredicate = new SchoolContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(personPredicate);
            break;
        case CATEGORY_SUBJECT:
            personPredicate = new SubjectContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(personPredicate);
            break;
        default:
            // invalid category should be detected in parser instead
            assert (false);
        }
        return new CommandResult(MESSAGE_SUCCESS + "\n"
                + getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && category.equals(((FindCommand) other).category)
                && hasSameValue(keywords, ((FindCommand) other).keywords));
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

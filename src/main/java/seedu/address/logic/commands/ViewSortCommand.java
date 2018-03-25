package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.model.person.Person;
import seedu.address.model.tutee.EducationLevelContainsKeywordsPredicate;
import seedu.address.model.tutee.GradeContainsKeywordsPredicate;
import seedu.address.model.tutee.SchoolContainsKeywordsPredicate;
import seedu.address.model.tutee.SubjectContainsKeywordsPredicate;

/**
 * Views a sorted person or task list that suits the specified category.
 */
public class ViewSortCommand extends Command {
    public static final String COMMAND_WORD = "viewSort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private static final String CATEGORY_MONTH = "month";
    private static final String CATEGORY_EDUCATION_LEVEL = "level";
    private static final String CATEGORY_GRADE = "grade";
    private static final String CATEGORY_SCHOOL = "school";
    private static final String CATEGORY_SUBJECT = "subject";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": view a sorted person or task list that suits the specified category\n"
            + "Parameters: sort-category keyword\n"
            + "Choice of categories: "
            + CATEGORY_MONTH + ", "
            + CATEGORY_EDUCATION_LEVEL + ", "
            + CATEGORY_GRADE + ", "
            + CATEGORY_SCHOOL + ", "
            + CATEGORY_SUBJECT + "\n"
            + "Example: " + COMMAND_WORD + " " + CATEGORY_GRADE + " A";

    private final String category;
    private final String[] keywords;
    private Predicate<Person> predicate;

    public ViewSortCommand(String category, String[] keywords) {
        this.category = category;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        switch (category) {
        case CATEGORY_MONTH:
            break;
        case CATEGORY_EDUCATION_LEVEL:
            predicate = new EducationLevelContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(predicate);
            break;
        case CATEGORY_GRADE:
            predicate = new GradeContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(predicate);
            break;
        case CATEGORY_SCHOOL:
            predicate = new SchoolContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(predicate);
            break;
        case CATEGORY_SUBJECT:
            predicate = new SubjectContainsKeywordsPredicate(Arrays.asList(keywords));
            model.updateFilteredPersonList(predicate);
            break;
        default:
            // invalid category should be detected in parser
            assert (false);
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
}

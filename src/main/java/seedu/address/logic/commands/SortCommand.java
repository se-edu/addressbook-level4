package seedu.address.logic.commands;

import static seedu.address.model.person.PersonSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_GRADE;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_NAME;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SUBJECT;

import java.util.Comparator;

import seedu.address.model.person.Person;
import seedu.address.model.person.PersonSortUtil;

/**
 * Sorts and lists all persons in address book lexicographically by the given sorting category.
 * Since tutee contains specific information such as grades,
 * a Person who is not a tutee will be listed last when such information is selected to be the sorting category.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "sorted successfully";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sorts and lists all person that suit the sorting category\n"
            + "Since tutee contains specific information such as grades and school, \n"
            + "Person who are not Tutees will be listed last "
            + "when such information is selected as the sorting category."
            + "Parameters: sort_category\n"
            + "Choice of sort_categories: "
            + CATEGORY_NAME + ", "
            + CATEGORY_EDUCATION_LEVEL + ", "
            + CATEGORY_GRADE + ", "
            + CATEGORY_SCHOOL + ", "
            + CATEGORY_SUBJECT + "\n"
            + "Example: " + COMMAND_WORD + " " + CATEGORY_GRADE;

    private final String category;
    private final Comparator<Person> comparator;

    public SortCommand(String category) {
        this.category = category;
        comparator = new PersonSortUtil().getComparator(category);
    }

    @Override
    public CommandResult execute() {
        switch (category) {
        case CATEGORY_NAME:
            model.sortFilteredPersonList(comparator);
            break;
        case CATEGORY_EDUCATION_LEVEL:
            break;
        case CATEGORY_GRADE:
            break;
        case CATEGORY_SCHOOL:
            break;
        case CATEGORY_SUBJECT:
            model.sortFilteredPersonList(comparator);
            break;
        default:
            // invalid category should be detected in parser
            assert (false);
        }
        return new CommandResult(MESSAGE_SUCCESS + "\n"
                + getMessageForPersonListShownSummary(model.getSortedAndFilteredPersonList().size()));
    }
}

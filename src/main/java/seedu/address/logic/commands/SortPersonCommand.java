package seedu.address.logic.commands;

import static seedu.address.model.person.PersonSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_GRADE;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_NAME;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SUBJECT;

import java.util.Comparator;

import seedu.address.model.person.Person;
import seedu.address.model.person.PersonSortUtil;

//@@author yungyung04
/**
 * Sorts all persons from the last shown list lexicographically according to the specified sorting category.
 * Since tutee contains specific information such as grade,
 * a Person who is not a tutee will be listed last when such information is selected to be the sorting category.
 */
public class SortPersonCommand extends Command {
    public static final String COMMAND_WORD = "sortpersonby";
    public static final String COMMAND_ALIAS = "spb";

    public static final String MESSAGE_SUCCESS = "sorted list of persons successfully";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sorts all visible persons lexicographically according to the specified sorting category.\n"
            + "Persons who are not Tutees will be listed last when a tutee detail is the selected category "
            + "(refer to User Guide)\n"
            + "Parameter: sort_category\n"
            + "Choice of sort_categories: "
            + CATEGORY_NAME + ", "
            + CATEGORY_EDUCATION_LEVEL + ", "
            + CATEGORY_GRADE + ", "
            + CATEGORY_SCHOOL + ", "
            + CATEGORY_SUBJECT + "\n"
            + "Example: " + COMMAND_WORD + " " + CATEGORY_GRADE;

    private final String category;
    private final Comparator<Person> comparator;

    public SortPersonCommand(String category) {
        this.category = category;
        comparator = new PersonSortUtil().getComparator(category);
    }

    @Override
    public CommandResult execute() {
        model.sortFilteredPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortPersonCommand // instanceof handles nulls
                && category.equals(((SortPersonCommand) other).category));
    }
}

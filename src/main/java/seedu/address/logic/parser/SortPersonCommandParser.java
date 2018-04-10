package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SORTER_CATEGORY;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_GRADE;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_NAME;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SUBJECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.SortPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortPersonCommand object
 */
public class SortPersonCommandParser implements Parser<SortPersonCommand> {

    private static final String SORT_CATEGORY_VALIDATION_REGEX = "\\p{Alpha}+";

    private List<String> validCategories =
            new ArrayList<>(Arrays.asList(CATEGORY_NAME, CATEGORY_EDUCATION_LEVEL,
                    CATEGORY_GRADE, CATEGORY_SCHOOL, CATEGORY_SUBJECT));

    /**
     * Parses the given {@code String} of arguments in the context of the SortPersonCommand
     * and returns a SortPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortPersonCommand parse(String args) throws ParseException {
        String sortCategory = args.trim().toLowerCase();

        if (!sortCategory.matches(SORT_CATEGORY_VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));
        }
        if (!validCategories.contains(sortCategory)) {
            throw new ParseException(String.format(MESSAGE_INVALID_SORTER_CATEGORY, SortPersonCommand.MESSAGE_USAGE));
        }
        return new SortPersonCommand(sortCategory);
    }
}

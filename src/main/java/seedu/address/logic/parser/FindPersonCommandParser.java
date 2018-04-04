package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILTER_CATEGORY;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_GRADE;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_NAME;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SUBJECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author yungyung04
/**
 * Parses input arguments and creates a new FindPersonCommand object
 */
public class FindPersonCommandParser implements Parser<FindPersonCommand> {

    private static final int EXPECTED_AMOUNT_OF_PARAMETERS = 2;
    private static final int INDEX_OF_FILTER_CATEGORY = 0;
    private static final int INDEX_OF_KEYWORDS = 1;

    private List<String> validCategories =
            new ArrayList<>(Arrays.asList(CATEGORY_NAME, CATEGORY_EDUCATION_LEVEL, CATEGORY_GRADE,
                    CATEGORY_SCHOOL, CATEGORY_SUBJECT));

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a FindPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonCommand parse(String args) throws ParseException {
        String[] arguments = args.trim().toLowerCase().split("\\s+", EXPECTED_AMOUNT_OF_PARAMETERS);

        if (arguments.length < EXPECTED_AMOUNT_OF_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }

        String filterCategory = arguments[INDEX_OF_FILTER_CATEGORY];
        String[] keywords = arguments[INDEX_OF_KEYWORDS].split("\\s+");

        if (!validCategories.contains(filterCategory)) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER_CATEGORY, FindPersonCommand.MESSAGE_USAGE));
        }

        return new FindPersonCommand(filterCategory, keywords);
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILTER_CATEGORY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.ViewSortCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddTuitionTaskCommand object
 */
public class ViewSortCommandParser implements Parser<ViewSortCommand> {

    private static final String CATEGORY_MONTH = "month";
    private static final String CATEGORY_GRADE = "grade";
    private static final String CATEGORY_SCHOOL = "school";
    private static final String CATEGORY_SUBJECT = "subject";
    private static final int EXPECTED_AMOUNT_OF_PARAMETERS = 2;
    private static final int INDEX_OF_FILTER_CATEGORY = 0;
    private static final int INDEX_OF_KEYWORDS = 1;

    private List<String> validCategories =
            new ArrayList<>(Arrays.asList(CATEGORY_MONTH, CATEGORY_GRADE, CATEGORY_SCHOOL, CATEGORY_SUBJECT));

    /**
     * Parses the given {@code String} of arguments in the context of the ViewSortCommand
     * and returns an ViewSortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewSortCommand parse(String args) throws ParseException {
        String[] arguments = args.trim().toLowerCase().split("\\s+", EXPECTED_AMOUNT_OF_PARAMETERS);

        if (arguments.length < EXPECTED_AMOUNT_OF_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewSortCommand.MESSAGE_USAGE));
        }

        String filterCategory = arguments[INDEX_OF_FILTER_CATEGORY];
        String[] keywords = arguments[INDEX_OF_KEYWORDS].split("\\s+");

        if (!validCategories.contains(filterCategory)) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER_CATEGORY, ViewSortCommand.MESSAGE_USAGE));
        }

        return new ViewSortCommand(filterCategory, keywords);
    }
}

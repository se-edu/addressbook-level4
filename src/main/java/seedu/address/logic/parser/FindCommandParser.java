package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILTER_CATEGORY;
import static seedu.address.model.tutee.TuteeSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.tutee.TuteeSortUtil.CATEGORY_GRADE;
import static seedu.address.model.tutee.TuteeSortUtil.CATEGORY_MONTH;
import static seedu.address.model.tutee.TuteeSortUtil.CATEGORY_NAME;
import static seedu.address.model.tutee.TuteeSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.tutee.TuteeSortUtil.CATEGORY_SUBJECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddTuitionTaskCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final int EXPECTED_AMOUNT_OF_PARAMETERS = 2;
    private static final int INDEX_OF_FILTER_CATEGORY = 0;
    private static final int INDEX_OF_KEYWORDS = 1;

    private List<String> validCategories =
            new ArrayList<>(Arrays.asList(CATEGORY_NAME, CATEGORY_MONTH,
                    CATEGORY_EDUCATION_LEVEL, CATEGORY_GRADE, CATEGORY_SCHOOL, CATEGORY_SUBJECT));

    /**
     * Parses the given {@code String} of arguments in the context of the ViewSortCommand
     * and returns an ViewSortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String[] arguments = args.trim().toLowerCase().split("\\s+", EXPECTED_AMOUNT_OF_PARAMETERS);

        if (arguments.length < EXPECTED_AMOUNT_OF_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String filterCategory = arguments[INDEX_OF_FILTER_CATEGORY];
        String[] keywords = arguments[INDEX_OF_KEYWORDS].split("\\s+");

        if (!validCategories.contains(filterCategory)) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER_CATEGORY, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(filterCategory, keywords);
    }
}

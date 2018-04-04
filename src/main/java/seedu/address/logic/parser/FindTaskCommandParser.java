package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILTER_CATEGORY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT_TYPES;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MONTH_RANGE_FORMAT;
import static seedu.address.model.task.TaskSortUtil.CATEGORY_DURATION;
import static seedu.address.model.task.TaskSortUtil.CATEGORY_MONTH;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import seedu.address.logic.commands.FindTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author yungyung04
/**
 * Parses input arguments and creates a new FindTaskCommand object
 */
public class FindTaskCommandParser implements Parser<FindTaskCommand> {

    private static final int EXPECTED_AMOUNT_OF_PARAMETERS = 3;
    private static final int INDEX_OF_FILTER_CATEGORY = 0;
    private static final int INDEX_OF_INPUT_TYPE = 1;
    private static final int INDEX_OF_KEYWORDS = 2;
    private static final int MONTH_WITH_MMM_FORMAT_CHARACTER_LENGTH = 3;
    private static final int EXPECTED_AMOUNT_OF_MONTHS = 2;
    private static final int MONTH_WITH_MM_FORMAT_CHARACTER_LENGTH = 2;

    private List<String> validCategories = new ArrayList<>(Arrays.asList(CATEGORY_MONTH, CATEGORY_DURATION));
    private List<String> validMonthInputTypes = new ArrayList<>(Arrays.asList("exact", "range"));

    /**
     * Parses the given {@code String} of arguments in the context of the FindTaskCommand
     * and returns a FindTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTaskCommand parse(String args) throws ParseException {
        String[] arguments = args.trim().toLowerCase().split("\\s+", EXPECTED_AMOUNT_OF_PARAMETERS);

        if (arguments.length < EXPECTED_AMOUNT_OF_PARAMETERS) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        }

        String filterCategory = arguments[INDEX_OF_FILTER_CATEGORY];

        if (!validCategories.contains(filterCategory)) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER_CATEGORY, FindTaskCommand.MESSAGE_USAGE));
        }

        String inputType = arguments[INDEX_OF_INPUT_TYPE];
        String[] keywords = arguments[INDEX_OF_KEYWORDS].split("\\s+");
        int[] months;

        switch (filterCategory) {
        case CATEGORY_MONTH:
            if (!validMonthInputTypes.contains(inputType)) {
                throw new ParseException(String.format(MESSAGE_INVALID_INPUT_TYPES, FindTaskCommand.MESSAGE_USAGE));
            }

            try {
                months = parseMonthsAsInteger(keywords);
            } catch (DateTimeParseException dtpe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
            }

            if (inputType.equals("range")) {
                if (!isValidMonthRange(months)) {
                    throw new ParseException(MESSAGE_INVALID_MONTH_RANGE_FORMAT);
                }
                months = getEntireMonthKeywords(months);
            }
            keywords = getMonthsAsStrings(months);
            break;
        default:
            System.out.println("should never be called");
        }
        return new FindTaskCommand(filterCategory, keywords);
    }

    private String[] getMonthsAsStrings(int[] months) {
        String[] keywords = new String[months.length];
        for (int i = 0; i < months.length; i++) {
            keywords[i] = Integer.toString(months[i]);
        }
        return keywords;
    }

    private int[] getEntireMonthKeywords(int[] months) {
        int firstMonthIndex = 0;
        int secondMonthIndex = 1;
        int monthRangeSize = months[secondMonthIndex] - months[firstMonthIndex] + 1;
        int[] monthsWithinRange = new int[monthRangeSize];

        for (int i = 0; i < monthRangeSize; i++) {
            monthsWithinRange[i] = months[firstMonthIndex] + i;
        }
        return monthsWithinRange;
    }

    private boolean isValidMonthRange(int[] months) {
        int firstMonthIndex = 0;
        int secondMonthIndex = 1;
        return months.length == EXPECTED_AMOUNT_OF_MONTHS && months[firstMonthIndex] < months[secondMonthIndex];
    }

    /**
     * will add comment later
     * @throws DateTimeParseException
     */
    private int[] parseMonthsAsInteger(String[] keywords) throws DateTimeParseException {
        DateTimeFormatter formatDigitMonth = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .appendPattern("MM").toFormatter(Locale.ENGLISH);
        DateTimeFormatter formatShortMonth = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .appendPattern("MMM").toFormatter(Locale.ENGLISH);
        DateTimeFormatter formatFullMonth = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .appendPattern("MMMM").toFormatter(Locale.ENGLISH);
        TemporalAccessor accessor;
        int[] months = new int[keywords.length];

        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i].length() < MONTH_WITH_MM_FORMAT_CHARACTER_LENGTH) {
                accessor = formatDigitMonth.parse("0" + keywords[i]);
                months[i] = accessor.get(ChronoField.MONTH_OF_YEAR);
            } else if (keywords[i].length() == MONTH_WITH_MM_FORMAT_CHARACTER_LENGTH) {
                accessor = formatDigitMonth.parse(keywords[i]);
                months[i] = accessor.get(ChronoField.MONTH_OF_YEAR);
            } else if (keywords[i].length() == MONTH_WITH_MMM_FORMAT_CHARACTER_LENGTH) {
                accessor = formatShortMonth.parse(keywords[i]);
                months[i] = accessor.get(ChronoField.MONTH_OF_YEAR);
            } else if (keywords[i].length() > MONTH_WITH_MMM_FORMAT_CHARACTER_LENGTH) {
                accessor = formatFullMonth.parse(keywords[i]);
                months[i] = accessor.get(ChronoField.MONTH_OF_YEAR);
            }
        }
        return months;
    }
}

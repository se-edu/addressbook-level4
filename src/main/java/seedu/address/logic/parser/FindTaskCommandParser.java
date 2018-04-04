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
import seedu.address.logic.parser.exceptions.InvalidBoundariesException;
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
    private static final int INDEX_OF_FIRST_KEYWORD = 0;
    private static final int INDEX_OF_SECOND_KEYWORD = 1;
    private static final int MONTH_WITH_MMM_FORMAT_CHARACTER_LENGTH = 3;
    private static final int EXPECTED_AMOUNT_OF_MONTHS = 2;
    private static final int MONTH_WITH_MM_FORMAT_CHARACTER_LENGTH = 2;
    private static final int AMOUNT_OF_MONTHS = 12;
    private static final String INPUT_TYPE_NAMELY = "namely";
    private static final String INPUT_TYPE_BETWEEN = "between";

    private List<String> validCategories = new ArrayList<>(Arrays.asList(CATEGORY_MONTH, CATEGORY_DURATION));
    private List<String> validMonthInputTypes = new ArrayList<>(Arrays.asList(INPUT_TYPE_NAMELY, INPUT_TYPE_BETWEEN));

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
            try {
                keywords = parseMonthKeywords(inputType, keywords);
            } catch (DateTimeParseException dtpe) {
                throw new ParseException(String.format(MESSAGE_INVALID_INPUT_TYPES, FindTaskCommand.MESSAGE_USAGE));
            } catch (InvalidBoundariesException ibe) {
                throw new ParseException(MESSAGE_INVALID_MONTH_RANGE_FORMAT);
            }
            break;
        default:
            assert (false); // should never be called
        }
        return new FindTaskCommand(filterCategory, keywords);
    }

    private String[] parseMonthKeywords(String inputType, String[] keywords) throws ParseException,
            DateTimeParseException, InvalidBoundariesException {
        int[] months;
        if (!validMonthInputTypes.contains(inputType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_INPUT_TYPES, FindTaskCommand.MESSAGE_USAGE));
        }
            months = parseMonthsAsInteger(keywords);

        if (inputType.equals(INPUT_TYPE_BETWEEN)) {
            if (!hasValidMonthBoundaries(months)) {
                throw new InvalidBoundariesException();
            }
            months = getAllMonthsBetweenBoundaries(months[INDEX_OF_FIRST_KEYWORD], months[INDEX_OF_SECOND_KEYWORD]);
        }
        keywords = convertIntoStrings(months);
        return keywords;
    }

    /**
     * Converts an array of integer into an array of String with the same value.
     */
    private String[] convertIntoStrings(int[] integers) {
        String[] strings = new String[integers.length];
        for (int i = 0; i < integers.length; i++) {
            strings[i] = Integer.toString(integers[i]);
        }
        return strings;
    }

    /**
     * Returns all months given two month boundaries.
     */
    private int[] getAllMonthsBetweenBoundaries(int lowerBoundary, int upperBoundary) {
        int monthDifference;
        int[] monthsWithinRange;

        if (lowerBoundary < upperBoundary) {
            monthDifference = upperBoundary - lowerBoundary + 1;
            monthsWithinRange = new int[monthDifference];
            for (int i = 0; i < monthDifference; i++) {
                monthsWithinRange[i] = lowerBoundary + i;
            }
        } else {
            monthDifference = upperBoundary + AMOUNT_OF_MONTHS + 1 - lowerBoundary;
            monthsWithinRange = new int[monthDifference];
            for (int i = 0; i < monthDifference; i++) {
                if (lowerBoundary + i <= AMOUNT_OF_MONTHS) {
                    monthsWithinRange[i] = lowerBoundary + i;
                } else {
                    monthsWithinRange[i] = lowerBoundary + i - AMOUNT_OF_MONTHS;
                }
            }
        }
        return monthsWithinRange;
    }

    /**
     * Returns true if the given months are valid boundaries.
     */
    private boolean hasValidMonthBoundaries(int[] months) {
        return months.length == EXPECTED_AMOUNT_OF_MONTHS
                && months[INDEX_OF_FIRST_KEYWORD] != months[INDEX_OF_SECOND_KEYWORD];
    }

    /**
     * Parses given {@code String[] months} into their integer representation.
     * @throws DateTimeParseException if any of the given month is invalid.
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

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILTER_CATEGORY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT_TYPES;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_KEYWORD_GIVEN;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MONTH_RANGE_FORMAT;
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
    private static final int INVALID_MONTH = -1;
    private static final int MONTH_WITH_MMM_FORMAT_CHARACTER_LENGTH = 3;
    private static final int REQUIRED_AMOUNT_OF_BOUNDARIES = 2;
    private static final int MONTH_WITH_MM_FORMAT_CHARACTER_LENGTH = 2;
    private static final int AMOUNT_OF_MONTHS = 12;
    private static final String INPUT_TYPE_NAMELY = "namely";
    private static final String INPUT_TYPE_BETWEEN = "between";
    private static final DateTimeFormatter FORMATTER_MONTH_MM = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("MM").toFormatter(Locale.ENGLISH);
    private static final DateTimeFormatter FORMATTER_MONTH_MMM = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("MMM").toFormatter(Locale.ENGLISH);
    private static final DateTimeFormatter FORMATER_MONTH_MMMM = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("MMMM").toFormatter(Locale.ENGLISH);

    private List<String> validCategories = new ArrayList<>(Arrays.asList(CATEGORY_MONTH));
    private List<String> validInputTypes = new ArrayList<>(Arrays.asList(INPUT_TYPE_NAMELY, INPUT_TYPE_BETWEEN));

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
        if (!validInputTypes.contains(inputType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_INPUT_TYPES, FindTaskCommand.MESSAGE_USAGE));
        }

        String[] keywords = arguments[INDEX_OF_KEYWORDS].split("\\s+");

        try {
            switch (filterCategory) {
            case CATEGORY_MONTH:
                keywords = parseMonthKeywords(inputType, keywords);
                break;
            default:
                assert (false); // should never be called
            }
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_KEYWORD_GIVEN);
        } catch (InvalidBoundariesException ibe) {
            throw new ParseException(MESSAGE_INVALID_MONTH_RANGE_FORMAT);
        }
        return new FindTaskCommand(filterCategory, keywords);
    }

    /**
     * Parses month keywords into the required form for the purpose of creating a FindTaskCommand
     * @throws ParseException if the given input type is not recognized.
     * @throws DateTimeParseException if any of the keywords given is an invalid month
     * @throws InvalidBoundariesException if the given keywords are invalid boundary values
     */
    private String[] parseMonthKeywords(String inputType, String[] keywords) throws DateTimeParseException,
            InvalidBoundariesException {
        int[] months;
        String[] convertedKeywords = NaturalLanguageIdentifier.getInstance()
                .convertNaturalLanguagesIntoMonths(keywords);
        months = parseMonthsAsIntegers(convertedKeywords);
        if (inputType.equals(INPUT_TYPE_BETWEEN)) {
            if (!hasValidMonthBoundaries(months)) {
                throw new InvalidBoundariesException();
            }
            months = getAllMonthsBetweenBoundaries(months[INDEX_OF_FIRST_KEYWORD], months[INDEX_OF_SECOND_KEYWORD]);
        }
        convertedKeywords = convertIntoStrings(months);
        return convertedKeywords;
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
        return months.length == REQUIRED_AMOUNT_OF_BOUNDARIES
                && months[INDEX_OF_FIRST_KEYWORD] != months[INDEX_OF_SECOND_KEYWORD];
    }

    /**
     * Parses given {@code String[]} of months into their integer representation.
     * @throws DateTimeParseException if any of the given month is invalid.
     */
    private int[] parseMonthsAsIntegers(String[] keywords) throws DateTimeParseException {
        int[] months = new int[keywords.length];
        for (int i = 0; i < keywords.length; i++) {
            months[i] = parseMonthAsInteger(keywords[i]);
        }
        return months;
    }

    /**
     * Parses given {@code String} of month into its integer representation.
     * @throws DateTimeParseException if the given month is invalid.
     */
    private int parseMonthAsInteger(String monthString) throws DateTimeParseException {
        TemporalAccessor accessor;
        int month = INVALID_MONTH;
        if (monthString.length() < MONTH_WITH_MM_FORMAT_CHARACTER_LENGTH) {
            accessor = FORMATTER_MONTH_MM.parse("0" + monthString);
            month = accessor.get(ChronoField.MONTH_OF_YEAR);
        } else if (monthString.length() == MONTH_WITH_MM_FORMAT_CHARACTER_LENGTH) {
            accessor = FORMATTER_MONTH_MM.parse(monthString);
            month = accessor.get(ChronoField.MONTH_OF_YEAR);
        } else if (monthString.length() == MONTH_WITH_MMM_FORMAT_CHARACTER_LENGTH) {
            accessor = FORMATTER_MONTH_MMM.parse(monthString);
            month = accessor.get(ChronoField.MONTH_OF_YEAR);
        } else if (monthString.length() > MONTH_WITH_MMM_FORMAT_CHARACTER_LENGTH) {
            accessor = FORMATER_MONTH_MMMM.parse(monthString);
            month = accessor.get(ChronoField.MONTH_OF_YEAR);
        }
        return month;
    }
}

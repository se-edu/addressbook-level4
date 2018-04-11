package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.SameTimeUnitException;

//@@author ChoChihTun
/**
 * Parses input arguments and creates a new ChangeCommand object
 */
public class ChangeCommandParser implements Parser<ChangeCommand> {
    private static final String DAY = "d";
    private static final String WEEK = "w";
    private static final String MONTH = "m";
    private static final String YEAR = "y";

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeCommand
     * and returns an ChangeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeCommand parse(String args) throws ParseException {
        try {
            String timeUnit = ParserUtil.parseTimeUnit(args);
            return new ChangeCommand(timeUnit);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        } catch (SameTimeUnitException stue) {
            throw new ParseException(stue.getMessage());
        }
    }

    /**
     * Checks if the user input view page time unit is valid
     *
     * @param trimmedTimeUnit to be checked
     * @return true if view page time unit is valid
     *          false if the view page time unit is invalid
     */
    public static boolean isValidTimeUnit(String trimmedTimeUnit) {
        return (trimmedTimeUnit.equals(DAY)
            || trimmedTimeUnit.equals(WEEK)
            || trimmedTimeUnit.equals(MONTH)
            || trimmedTimeUnit.equals(YEAR));
    }

    /**
     * Checks if the new view page time unit clashes with the current time unit
     *
     * @param timeUnit to be checked
     * @return true if the view page time unit clashes with the current time unit
     *          false if there is no clash
     */
    public static boolean isTimeUnitClash(String timeUnit) {
        String currentViewPage = ChangeCommand.getTimeUnit();
        return (timeUnit.equals(currentViewPage));
    }
}

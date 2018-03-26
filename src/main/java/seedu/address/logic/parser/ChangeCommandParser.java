package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.SameViewPageException;


/**
 * Parses input arguments and creases a new ChangeCommand object
 */
public class ChangeCommandParser implements Parser<ChangeCommand> {
    private static final String DAY = "d";
    private static final String WEEK = "w";
    private static final String MONTH = "m";
    private static final String YEAR = "y";

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeCommand
     * and returns an ChangeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeCommand parse(String args) throws ParseException {
        try {
            String viewPage = ParserUtil.parseViewPage(args);
            return new ChangeCommand(viewPage);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        } catch (SameViewPageException svpe) {
            throw new ParseException(svpe.getMessage());
        }
    }

    /**
     * Checks if the user input time unit is valid
     *
     * @param trimmedViewPage to be checked
     * @return true if view page time unit is valid
     *          false if the view page time unit is invalid
     */
    public static boolean isValidViewPage(String trimmedViewPage) {
        return (trimmedViewPage.equals(DAY)
            || trimmedViewPage.equals(WEEK)
            || trimmedViewPage.equals(MONTH)
            || trimmedViewPage.equals(YEAR));
    }

    /**
     * Checks if the new view page time unit clashes with the current time unit
     *
     * @param trimmedViewPage to be checked
     * @return true if the view page time unit clashes with the current time unit
     *          false if there is no clash
     */
    public static boolean isViewPageClash(String trimmedViewPage) {
        String currentViewPage = ChangeCommand.getViewPage();
        return (trimmedViewPage.equals(currentViewPage));
    }
}

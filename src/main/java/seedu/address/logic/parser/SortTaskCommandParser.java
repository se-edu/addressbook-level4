package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SORTER_CATEGORY;
import static seedu.address.model.task.TaskSortUtil.CATEGORY_DATE_TIME;
import static seedu.address.model.task.TaskSortUtil.CATEGORY_MONTH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.SortPersonCommand;
import seedu.address.logic.commands.SortTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author yungyung04
/**
 * Parses input arguments and creates a new SortTaskCommand object
 */
public class SortTaskCommandParser implements Parser<SortTaskCommand> {

    private static final String SORT_CATEGORY_VALIDATION_REGEX = "\\p{Alpha}+";

    private List<String> validCategories =
            new ArrayList<>(Arrays.asList(CATEGORY_MONTH, CATEGORY_DATE_TIME));

    /**
     * Parses the given {@code String} of arguments in the context of the SortTaskCommand
     * and returns a SortPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortTaskCommand parse(String args) throws ParseException {
        String sortCategory = args.trim().toLowerCase();

        if (!sortCategory.matches(SORT_CATEGORY_VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));
        }
        if (!validCategories.contains(sortCategory)) {
            throw new ParseException(String.format(MESSAGE_INVALID_SORTER_CATEGORY, SortPersonCommand.MESSAGE_USAGE));
        }
        return new SortTaskCommand(sortCategory);
    }
}

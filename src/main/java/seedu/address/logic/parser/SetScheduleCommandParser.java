package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.SetScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetScheduleCommand object
 */
public class SetScheduleCommandParser implements Parser<SetScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        return new SetScheduleCommand(args);
    }
}

package seedu.address.logic.parser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.KnockCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class KnockCommandParser implements Parser<KnockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public KnockCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, KnockCommand.MESSAGE_USAGE));
        }

        String[] inputs = trimmedArgs.split("\\s+");

        return inputs.length == 2? new KnockCommand(inputs[1]) : new KnockCommand();
    }
}

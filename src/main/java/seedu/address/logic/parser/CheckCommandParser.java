package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new {@code CheckCommand} object
 */
public class CheckCommandParser implements Parser<CheckCommand>{
    /**
     * Parses the given {@code String} of arguments in the context of the {@code CheckCommand}
     * and returns a {@code CheckCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CheckCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_PASSWORD, PREFIX_MODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_PASSWORD, PREFIX_MODE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
        }

        String nric = argMultimap.getValue(PREFIX_NRIC).orElse("");
        String password = argMultimap.getValue(PREFIX_PASSWORD).orElse("");
        String mode = argMultimap.getValue(PREFIX_MODE).orElse("");

        return new CheckCommand(nric, password, mode);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

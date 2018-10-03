package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.CreateCommand;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Accounts;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CreateCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommand
     * and returns an CreateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);


        String username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());
        String password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());


        Accounts account = new Accounts(username, password);

        return new CreateCommand(account);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

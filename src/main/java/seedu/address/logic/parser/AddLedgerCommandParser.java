package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BALANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.logic.commands.AddLedgerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;
import seedu.address.model.ledger.Ledger;

/**
 * Parses input command arguments and creates a new addLedgerCommand object
 */

public class AddLedgerCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLedgerCommand
     * and returns an AddLedgerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddLedgerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        DateLedger date = ParserUtil.parseDateLedger(argMultimap.getValue(PREFIX_DATE).get());
        //Account account = ParserUtil.parseBalance(Double.parseDouble(argMultimap.getValue(PREFIX_BALANCE).get()));

        Ledger ledger = new Ledger(date, new Account(0.0));

        return new AddLedgerCommand(ledger);
    }
}

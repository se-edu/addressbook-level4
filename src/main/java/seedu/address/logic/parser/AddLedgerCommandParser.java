package seedu.address.logic.parser;

import seedu.address.logic.commands.AddLedgerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;
import seedu.address.model.ledger.Ledger;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

public class AddLedgerCommandParser {

    public AddLedgerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        DateLedger date = ParserUtil.parseDateLedger(argMultimap.getValue(PREFIX_DATE).get());

        Ledger ledger = new Ledger(date);

        return new AddLedgerCommand(ledger);
    }
}

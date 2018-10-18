package seedu.address.logic.parser.ledger;

import seedu.address.logic.commands.ledger.CreditCommand;
import seedu.address.logic.commands.ledger.DebitCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ledger.DateLedger;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BALANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

public class DebitCommandParser {

    public DebitCommand parse (String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_BALANCE);

        DateLedger dateLedger = ParserUtil.parseDateLedger(argumentMultimap.getValue(PREFIX_DATE).get());
        Double amount = ParserUtil.parseBalance(argumentMultimap.getValue(PREFIX_BALANCE).get());

        return new DebitCommand(dateLedger, amount);
    }
}

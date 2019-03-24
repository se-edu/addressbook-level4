package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PURCHASENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddPurchaseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.purchase.PurchaseName;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.purchase.Price;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddPurchaseCommand object
 */
public class AddPurchaseCommandParser implements Parser<AddPurchaseCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPurchaseCommand
     * and returns an AddPurchaseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPurchaseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PURCHASENAME, PREFIX_PRICE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_PURCHASENAME, PREFIX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPurchaseCommand.MESSAGE_USAGE));
        }

        PurchaseName name = ParserUtil.parsePurchaseName(argMultimap.getValue(PREFIX_PURCHASENAME).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Purchase purchase = new Purchase(name, price, tagList);

        return new AddPurchaseCommand(purchase);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

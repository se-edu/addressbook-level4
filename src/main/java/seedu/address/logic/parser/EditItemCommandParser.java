package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditItemCommand;
import seedu.address.logic.commands.EditItemCommand.EditItemDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditItemCommand object
 */
public class EditItemCommandParser implements Parser<EditItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditItemCommand
     * and returns an EditItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM_NAME, PREFIX_ITEM_QUANTITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditItemCommand.MESSAGE_USAGE), pe);
        }

        EditItemDescriptor editItemDescriptor = new EditItemDescriptor();
        if (argMultimap.getValue(PREFIX_ITEM_NAME).isPresent()) {
            editItemDescriptor.setItemName(ParserUtil.parseItemName(argMultimap.getValue(PREFIX_ITEM_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ITEM_QUANTITY).isPresent()) {
            editItemDescriptor.setItemQuantity(ParserUtil
                    .parseItemQuantity(argMultimap.getValue(PREFIX_ITEM_QUANTITY).get()));
        }

        if (!editItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditItemCommand.MESSAGE_NOT_EDITED);
        }

        return new EditItemCommand(index, editItemDescriptor);
    }

}

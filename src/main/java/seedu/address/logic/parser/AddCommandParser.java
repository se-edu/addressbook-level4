package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(Parser.PREFIX_PHONE, Parser.PREFIX_EMAIL,
                Parser.PREFIX_ADDRESS, Parser.PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(Parser.PREFIX_PHONE).get(),
                    argsTokenizer.getValue(Parser.PREFIX_EMAIL).get(),
                    argsTokenizer.getValue(Parser.PREFIX_ADDRESS).get(),
                    Parser.toSet(argsTokenizer.getAllValues(Parser.PREFIX_TAG))
                    );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}

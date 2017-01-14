package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments in the context of the Add command
 */
public class AddCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_PHONE).get(),
                    argsTokenizer.getValue(PREFIX_EMAIL).get(),
                    argsTokenizer.getValue(PREFIX_ADDRESS).get(),
                    toSet(argsTokenizer.getAllValues(PREFIX_TAG))
                    );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private Set<String> toSet(Optional<List<String>> tagsOptional) {
        List<String> tags = tagsOptional.orElse(Collections.emptyList());
        return new HashSet<>(tags);
    }

}

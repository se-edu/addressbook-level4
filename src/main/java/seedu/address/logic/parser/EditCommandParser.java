package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptorBuilder;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argMultimap.getPreamble(), 2);

        if (!preambleFields.get(0).isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        int index;

        try {
            index = ParserUtil.parseIndex(preambleFields.get(0).get());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor;
        try {
            editPersonDescriptor = new EditPersonDescriptorBuilder()
                    .withName(preambleFields.get(1).orElse(null))
                    .withPhone(argMultimap.getValue(PREFIX_PHONE).orElse(null))
                    .withEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(null))
                    .withAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(null))
                    .withTags(parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).orElse(null)).build();
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<Collection<String>>}.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into an empty
     * {@code Collection<String>}.
     */
    private Optional<Collection<String>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(tagSet);
    }

}

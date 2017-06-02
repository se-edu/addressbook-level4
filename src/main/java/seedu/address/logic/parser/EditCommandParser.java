package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * @throws ParseException if the user input does not conform the expected format
     */
    public ParserResult<EditCommand> parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        List<Optional<String>> preambleFields = ParserUtil.split(argMultimap.getPreamble(), 2);

        if (!preambleFields.get(0).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(preambleFields.get(0).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Optional<String> warning = ParserUtil.getWarningMessage(
                argMultimap, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            editPersonDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)));
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)));
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)));
            editPersonDescriptor.setTags(parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new ParserResult<>(new EditCommand(index, editPersonDescriptor), warning.orElse(""));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

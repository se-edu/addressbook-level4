package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.Tag;

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

        List<String> recordedViolations = new ArrayList<String>();

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        try {
            editPersonDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
        } catch (IllegalValueException ive) {
            recordedViolations.add(ive.getMessage());
        }

        try {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)));
        } catch (IllegalValueException ive) {
            recordedViolations.add(ive.getMessage());
        }

        try {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)));
        } catch (IllegalValueException ive) {
            recordedViolations.add(ive.getMessage());
        }

        try {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)));
        } catch (IllegalValueException ive) {
            recordedViolations.add(ive.getMessage());
        }

        try {
            editPersonDescriptor.setTags(parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)));
        } catch (IllegalValueException ive) {
            recordedViolations.add(ive.getMessage());
        }

        if (!recordedViolations.isEmpty()) {
            return new IncorrectCommand(recordedViolations.stream().collect(Collectors.joining("\n")));
        }
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(index, editPersonDescriptor);

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

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        List<String> recordedViolations = new ArrayList<String>();

        Name name = null;
        Phone phone = null;
        Email email = null;
        Address address = null;
        Set<Tag> tagList = null;

        try {
            name = new Name(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            recordedViolations.add(ive.getMessage());
        }

        try {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
        } catch (IllegalValueException ive) {
            recordedViolations.add(ive.getMessage());
        }

        try {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
        } catch (IllegalValueException ive) {
            recordedViolations.add(ive.getMessage());
        }

        try {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
        } catch (IllegalValueException ive) {
            recordedViolations.add(ive.getMessage());
        }

        try {
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } catch (IllegalValueException ive) {
            recordedViolations.add(ive.getMessage());
        }

        if (recordedViolations.isEmpty()) {
            ReadOnlyPerson person = new Person(name, phone, email, address, tagList);
            return new AddCommand(person);
        } else {
            return new IncorrectCommand(recordedViolations.stream()
                    .collect(Collectors.joining("\n")));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

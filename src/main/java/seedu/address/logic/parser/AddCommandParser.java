package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            checkCompulsoryPrefixPresent(argsTokenizer, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL);

            Name name = new Name(argsTokenizer.getPreamble());
            Phone phone = ParserUtil.parsePhone(argsTokenizer.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argsTokenizer.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argsTokenizer.getValue(PREFIX_ADDRESS)).get();
            UniqueTagList tagList = ParserUtil.parseTags(argsTokenizer.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, email, address, tagList);

            return new AddCommand(person);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Verifies that the given prefixes does not contain an empty {@code Optional} value.
     *
     * @throws NoSuchElementException if any of the prefix given has an empty Optional value
     */
    private void checkCompulsoryPrefixPresent(ArgumentTokenizer argsTokenizer, Prefix... prefixes)
        throws NoSuchElementException {

        for (Prefix prefix : prefixes) {
            if (!argsTokenizer.getValue(prefix).isPresent()) {
                throw new NoSuchElementException();
            }
        }
    }

}

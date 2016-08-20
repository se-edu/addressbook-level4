package seedu.address.commands;

import seedu.address.commons.Utils;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.regex.Pattern;


/**
 * Adds a person to the address book.
 */
public class AddPersonCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Contact details can be marked private by prepending 'p' to the prefix.\n"
            + "Parameters: NAME [p]p/PHONE [p]e/EMAIL [p]a/ADDRESS  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    public static final Pattern ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                            + " (?<isPhonePrivate>p?)p/(?<phone>[^/]+)"
                            + " (?<isEmailPrivate>p?)e/(?<email>[^/]+)"
                            + " (?<isAddressPrivate>p?)a/(?<address>[^/]+)"
                            + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private final Person toAdd;

    public AddPersonCommand(Person toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyPerson getPerson() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        Utils.assertNotNull(addressBook);
        addressBook.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}

package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: NAME "
            + CliSyntax.PREFIX_PHONE.getPrefix() + "PHONE "
            + CliSyntax.PREFIX_EMAIL.getPrefix() + "EMAIL "
            + CliSyntax.PREFIX_ADDRESS.getPrefix() + "ADDRESS "
            + "[" + CliSyntax.PREFIX_TAG.getPrefix() + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " John Doe "
            + CliSyntax.PREFIX_PHONE.getPrefix() + "98765432 "
            + CliSyntax.PREFIX_EMAIL.getPrefix() + "johnd@example.com "
            + CliSyntax.PREFIX_ADDRESS.getPrefix() + "311, Clementi Ave 2, #02-25 "
            + CliSyntax.PREFIX_TAG.getPrefix() + "friends "
            + CliSyntax.PREFIX_TAG.getPrefix() + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddCommand(ReadOnlyPerson person) {
        toAdd = new Person(person);
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

}

package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: NAME p/PHONE e/EMAIL a/ADDRESS  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

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

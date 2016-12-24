package seedu.address.logic.commands;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

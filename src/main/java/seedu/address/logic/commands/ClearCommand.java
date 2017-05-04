package seedu.address.logic.commands;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        setData(new AddressBook(model.getAddressBook()));
        model.resetData(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

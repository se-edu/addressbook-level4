package seedu.address.logic.commands;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    private ReadOnlyAddressBook previousAddressBook;

    @Override
    public CommandResult execute() {
        assert model != null;
        previousAddressBook = new AddressBook(model.getAddressBook());
        model.resetData(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void undo() {
        assert previousAddressBook != null;
        model.resetData(previousAddressBook);
    }
}

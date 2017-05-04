package seedu.address.logic.commands;

import static com.google.common.base.Preconditions.checkNotNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.OutOfElementsException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

public abstract class ReversibleCommand extends Command {
    private ReadOnlyAddressBook previousAddressBook;

    /**
     * Stores the current state of {@code model#addressBook} in {@code previousAddressBook}.
     */
    protected final void saveAddressBookSnapshot() {
        checkNotNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
    }

    /**
     * Reverts the state of AddressBook to the state before
     * this command was executed and updates the filtered person
     * list to show all persons.
     */
    protected final void undo() {
        checkNotNull(model);
        checkNotNull(previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredListToShowAll();
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() throws OutOfElementsException {
        checkNotNull(model);
        try {
            execute();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredListToShowAll();
    }
}

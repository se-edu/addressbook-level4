package seedu.address.logic.commands;

import static com.google.common.base.Preconditions.checkNotNull;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

public abstract class ReversibleCommand extends Command {
    protected ReadOnlyAddressBook previousAddressBook;

    /**
     * Stores the current state of {@code model#addressBook} in {@code previousAddressBook}.
     */
    protected final void saveAddressBookSnapshot() {
        checkNotNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
    }

    /**
     * Reverts the state of AddressBook to the state before
     * this command was executed.
     */
    public final void rollback() {
        checkNotNull(model);
        checkNotNull(previousAddressBook);
        model.resetData(previousAddressBook);
    }
}

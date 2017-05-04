package seedu.address.logic.commands;

import static com.google.common.base.Preconditions.checkNotNull;

import seedu.address.model.ReadOnlyAddressBook;

public abstract class ReversibleCommand extends Command {
    protected ReadOnlyAddressBook previousAddressBook;

    public final void setData(ReadOnlyAddressBook previousAddressBook) {
        this.previousAddressBook = previousAddressBook;
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

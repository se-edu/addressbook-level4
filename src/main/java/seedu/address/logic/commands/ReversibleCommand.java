package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.OutOfElementsException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class ReversibleCommand extends Command {
    private ReadOnlyAddressBook previousAddressBook;

    abstract CommandResult executeReversibleCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
    }

    /**
     * Reverts the state of AddressBook to the state before
     * this command was executed and updates the filtered person
     * list to show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredListToShowAll();
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() throws OutOfElementsException {
        requireNonNull(model);
        try {
            executeReversibleCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredListToShowAll();
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeReversibleCommand();
    }
}

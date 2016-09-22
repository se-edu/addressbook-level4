package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.model.UnmodifiableObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyPerson> lastShownList = modelManager.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            return new IncorrectCommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToDelete = lastShownList.get(targetIndex - 1);

        try {
            modelManager.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }
}

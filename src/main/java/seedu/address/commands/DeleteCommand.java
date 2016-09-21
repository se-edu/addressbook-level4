package seedu.address.commands;

import seedu.address.commons.Messages;
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

    public final ReadOnlyPerson targetPerson;

    public DeleteCommand(ReadOnlyPerson targetPerson) {
        this.targetPerson = targetPerson;
    }


    @Override
    public CommandResult execute() {
        try {
            modelManager.deletePerson(targetPerson);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }
}

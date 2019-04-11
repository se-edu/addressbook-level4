package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ContactList;
import seedu.address.model.Model;

/**
 * Clears the contact list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Contact List has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setContactList(new ContactList());
        model.commitContactList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

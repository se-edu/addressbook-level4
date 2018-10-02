package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.schedule.ScheduleList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetAddressBookData(new AddressBook());
        model.resetScheduleListData(new ScheduleList());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

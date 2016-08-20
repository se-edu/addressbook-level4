package seedu.address.commands;

import seedu.address.model.person.ReadOnlyPerson;

import java.util.List;


/**
 * Lists all persons in the address book to the user.
 */
public class ListAllPersonsCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all persons in the address book as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD;


    public ListAllPersonsCommand() {}


    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> allPersons = addressBook.getPersonList();
        return new CommandResult(getMessageForPersonListShownSummary(allPersons), allPersons);
    }
}

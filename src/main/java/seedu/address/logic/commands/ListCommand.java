package seedu.address.logic.commands;

import java.util.Collections;


/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all persons in the address book as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public ListCommand() {}


    @Override
    public CommandResult execute() {
        modelManager.clearListFilter();
        return new CommandResult(MESSAGE_SUCCESS, Collections.emptyList());
    }
}

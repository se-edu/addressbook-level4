package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

import javafx.collections.ObservableList;

/**
 * Sorts all persons in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all clients!";

    public SortCommand(){
    }
    @Override
    public CommandResult execute() throws CommandException {

        ObservableList<Person> shownList = model.getFilteredPersonList();
        if(shownList.isEmpty()){
            throw new CommandException(Messages.MESSAGE_PERSON_LIST_EMPTY);
        }
        model.sortFilteredPersonList(shownList);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

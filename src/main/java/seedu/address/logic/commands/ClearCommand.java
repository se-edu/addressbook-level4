package seedu.address.logic.commands;

import seedu.address.model.ToDo;

/**
 * Clears the SmartyDo.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(ToDo.getEmptyAddressBook());
        undoRedoManager.resetData();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

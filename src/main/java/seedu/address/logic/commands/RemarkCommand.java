package seedu.address.logic.commands;

import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException("Lol");
    }
}

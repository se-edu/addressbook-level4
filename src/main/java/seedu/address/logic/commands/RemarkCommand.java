package seedu.address.logic.commands;

import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;

public class RemarkCommand extends UndoableCommand {
    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException("Lol");
    }
}

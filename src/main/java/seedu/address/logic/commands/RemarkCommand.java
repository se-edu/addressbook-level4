package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " index(integer) str(String)";

    public final int INDEX;

    public final String STR;

    public RemarkCommand(int index, String str) {
        INDEX = index;
        STR = str;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(INDEX + " " + STR);
    }
}

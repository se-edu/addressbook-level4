package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Creates a remark for a person.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " index(integer) r/str(String)";

    private int index;

    private String str;

    public RemarkCommand(int index, String str) {
        this.index = index;
        this.str = str;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(index + " " + str);
    }
}

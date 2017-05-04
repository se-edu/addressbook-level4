package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Clears the address book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "There's no more commands left to be redone!";

    private Command nextCommand;

    public RedoCommand(ReversibleCommand command) {
        nextCommand = command;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        assert nextCommand != null;

        try {
            nextCommand.execute();
        } catch (CommandException e) {
            assert false : "";
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}

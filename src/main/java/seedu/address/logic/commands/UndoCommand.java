package seedu.address.logic.commands;

/**
 * Clears the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "There's no more commands left to be undone!";

    private ReversibleCommand previousCommand;

    public UndoCommand(ReversibleCommand command) {
        previousCommand = command;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        assert previousCommand != null;

        previousCommand.undo();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

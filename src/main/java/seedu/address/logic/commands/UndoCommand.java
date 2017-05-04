package seedu.address.logic.commands;

public class UndoCommand extends Command {
    public static final String MESSAGE_FAILURE = "There's no more commands left to be undone!";

    @Override
    public CommandResult execute() {
        return new CommandResult("stub");
    }
}

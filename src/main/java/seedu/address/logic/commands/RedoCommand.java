package seedu.address.logic.commands;

public class RedoCommand extends Command {
    public static final String MESSAGE_FAILURE = "There's no more commands left to be redone!";

    @Override
    public CommandResult execute() {
        return new CommandResult("stub");
    }
}

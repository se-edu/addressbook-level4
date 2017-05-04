package seedu.address.logic.commands;

public abstract class ReversibleCommand extends Command {
    public abstract void undo();
}

package seedu.address.logic;

import java.util.Optional;

import seedu.address.logic.commands.ReversibleCommand;

public interface History {
    public Optional<ReversibleCommand> getPreviousCommand();
    public Optional<ReversibleCommand> getNextCommand();
}

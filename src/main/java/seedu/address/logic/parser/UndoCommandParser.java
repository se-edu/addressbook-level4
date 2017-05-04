package seedu.address.logic.parser;

import java.util.Optional;

import seedu.address.logic.History;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ReversibleCommand;
import seedu.address.logic.commands.UndoCommand;

public class UndoCommandParser {

    private History history;

    public UndoCommandParser(History history) {
        this.history = history;
    }

    public Command parse() {
        Optional<ReversibleCommand> command = history.getPreviousCommand();
        if (command.isPresent()) {
            return new UndoCommand(command.get());
        } else {
            return new IncorrectCommand(UndoCommand.MESSAGE_FAILURE);
        }
    }
}

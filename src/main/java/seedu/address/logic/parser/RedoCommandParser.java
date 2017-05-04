package seedu.address.logic.parser;

import java.util.Optional;

import seedu.address.logic.History;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReversibleCommand;

public class RedoCommandParser {

    private History history;

    public RedoCommandParser(History history) {
        this.history = history;
    }

    public Command parse() {
        Optional<ReversibleCommand> command = history.getNextCommand();
        if (command.isPresent()) {
            return new RedoCommand(command.get());
        } else {
            return new IncorrectCommand(RedoCommand.MESSAGE_FAILURE);
        }
    }
}

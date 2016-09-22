package seedu.address.logic;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.ModelManager;
import seedu.address.logic.parser.Parser;

/**
 * The command executor of the app.
 */
public class Logic {
    private final ModelManager modelManager;
    private final Parser parser;

    public Logic(ModelManager modelManager) {
        this.modelManager = modelManager;
        this.parser = new Parser();
    }

    public CommandResult execute(String commandText) {
        Command command = parser.parseCommand(commandText);
        command.setData(modelManager);
        return command.execute();
    }
}

package seedu.address.logic;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.ModelManager;
import seedu.address.logic.parser.Parser;

/**
 * The main LogicManager of the app.
 */
public class LogicManager {
    private final ModelManager modelManager;
    private final Parser parser;

    public LogicManager(ModelManager modelManager) {
        this.modelManager = modelManager;
        this.parser = new Parser();
    }

    /**
     * Executes the command and returns the result.
     */
    public CommandResult execute(String commandText) {
        Command command = parser.parseCommand(commandText);
        command.setData(modelManager);
        return command.execute();
    }
}

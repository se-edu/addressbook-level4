package seedu.address.logic;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.Parser;
import seedu.address.model.ModelManager;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager{
    private static final Logger logger = LogsCenter.getLogger(LogicManager.class);
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

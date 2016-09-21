package seedu.address.logic;

import seedu.address.commands.Command;
import seedu.address.commands.CommandResult;
import seedu.address.model.ModelManager;
import seedu.address.parser.Parser;

/**
 * The command executor of the app.
 */
public class Logic {
    private final ModelManager modelManager;
    private final Parser parser;

    public Logic(ModelManager modelManager) {
        this.modelManager = modelManager;
        this.parser = new Parser();
        parser.configure(modelManager.getFilteredPersonList());
    }

    public CommandResult execute(String commandText) {
        Command command = parser.parseCommand(commandText);
        command.setData(modelManager);
        return command.execute();
    }
}

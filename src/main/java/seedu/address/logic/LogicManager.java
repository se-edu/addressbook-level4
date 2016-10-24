package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ConfirmCommand;
import seedu.address.logic.commands.RequiresConfirm;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.undoredomanager.UndoRedoManager;
import seedu.address.model.Model;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final UndoRedoManager undoRedoManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
        this.undoRedoManager = new UndoRedoManager();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model, undoRedoManager);
        if(command instanceof RequiresConfirm){
            return ((RequiresConfirm) command).prompt();
        }else if(!(command instanceof ConfirmCommand)){
            ConfirmCommand.AWAITINGCONFIRMATION = null;
        }
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}

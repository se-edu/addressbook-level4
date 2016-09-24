package seedu.address.logic;

import com.google.common.eventbus.Subscribe;
import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ModelChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.Parser;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.StorageManager;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager{
    private static final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final ModelManager modelManager;
    private final StorageManager storageManager;
    private final Parser parser;

    public LogicManager(ModelManager modelManager, StorageManager storageManager) {
        this.modelManager = modelManager;
        this.storageManager = storageManager;
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

    /**
     * Creates the file if it is missing before saving.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving
     */
    @Subscribe
    public void handleModelChangedEvent(ModelChangedEvent mce) {
        logger.info("Local data changed, saving to primary data file");
        try {
            storageManager.saveAddressBook(mce.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return modelManager.getFilteredPersonList();
    }
}

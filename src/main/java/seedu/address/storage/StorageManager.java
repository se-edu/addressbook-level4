package seedu.address.storage;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ToDoChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyToDo;
import seedu.address.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of ToDo data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ToDoStorage toDoStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ToDoStorage toDoStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.toDoStorage = toDoStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String toDoFilePath, String userPrefsFilePath) {
        this(new XmlAddressBookStorage(toDoFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ToDo methods ==============================

    @Override
    public String getToDoFilePath() {
        return toDoStorage.getToDoFilePath();
    }

    @Override
    public Optional<ReadOnlyToDo> readToDo() throws DataConversionException, IOException {
        return readToDo(toDoStorage.getToDoFilePath());
    }

    @Override
    public Optional<ReadOnlyToDo> readToDo(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return toDoStorage.readToDo(filePath);
    }

    @Override
    public void saveToDo(ReadOnlyToDo toDo) throws IOException {
        saveToDo(toDo, toDoStorage.getToDoFilePath());
    }

    @Override
    public void saveToDo(ReadOnlyToDo toDo, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        toDoStorage.saveToDo(toDo, filePath);
    }


    @Override
    @Subscribe
    public void handleToDoChangedEvent(ToDoChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveToDo(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}

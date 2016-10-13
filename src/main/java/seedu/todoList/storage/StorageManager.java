package seedu.todoList.storage;

import com.google.common.eventbus.Subscribe;

import seedu.todoList.commons.core.ComponentManager;
import seedu.todoList.commons.core.LogsCenter;
import seedu.todoList.commons.events.model.*;
import seedu.todoList.commons.events.storage.DataSavingExceptionEvent;
import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.model.ReadOnlyTaskList;
import seedu.todoList.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of TodoList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskListStorage todoListStorage;
    private TaskListStorage eventListStorage;
    private TaskListStorage deadlineListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskListStorage todoListStorage, TaskListStorage eventListStorage,
    						TaskListStorage deadlineListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.todoListStorage = todoListStorage;
        this.eventListStorage = eventListStorage;
        this.deadlineListStorage = deadlineListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String todoListFilePath, String eventListFilePath,
    						String deadlineListFilePath, String userPrefsFilePath) {
        this(new XmlTaskListStorage(todoListFilePath), new XmlTaskListStorage(eventListFilePath),
        		new XmlTaskListStorage(deadlineListFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ TodoList methods ==============================

    @Override
    public String getTodoListFilePath() {
        return todoListStorage.getTaskListFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskList> readTodoList() throws DataConversionException, IOException {
        return readTodoList(todoListStorage.getTaskListFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskList> readTodoList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return todoListStorage.readTaskList(filePath);
    }

    @Override
    public void saveTodoList(ReadOnlyTaskList TodoList) throws IOException {
        saveTodoList(TodoList, todoListStorage.getTaskListFilePath());
    }

    @Override
    public void saveTodoList(ReadOnlyTaskList TodoList, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        todoListStorage.saveTaskList(TodoList, filePath);
    }


    @Override
    @Subscribe
    public void handleTodoListChangedEvent(TodoListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTodoList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
 // ================ EventList methods ==============================

    @Override
    public String getEventListFilePath() {
        return eventListStorage.getTaskListFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskList> readEventList() throws DataConversionException, IOException {
        return readEventList(eventListStorage.getTaskListFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskList> readEventList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventListStorage.readTaskList(filePath);
    }

    @Override
    public void saveEventList(ReadOnlyTaskList eventList) throws IOException {
        saveEventList(eventList, todoListStorage.getTaskListFilePath());
    }

    @Override
    public void saveEventList(ReadOnlyTaskList eventList, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventListStorage.saveTaskList(eventList, filePath);
    }


    @Override
    @Subscribe
    public void handleEventListChangedEvent(EventListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEventList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
 // ================ DeadlineList methods ==============================

    @Override
    public String getDeadlineListFilePath() {
        return deadlineListStorage.getTaskListFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskList> readDeadlineList() throws DataConversionException, IOException {
        return readTodoList(deadlineListStorage.getTaskListFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskList> readDeadlineList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return deadlineListStorage.readTaskList(filePath);
    }

    @Override
    public void saveDeadlineList(ReadOnlyTaskList taskList) throws IOException {
        saveTodoList(taskList, deadlineListStorage.getTaskListFilePath());
    }

    @Override
    public void saveDeadlineList(ReadOnlyTaskList taskList, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        deadlineListStorage.saveTaskList(taskList, filePath);
    }


    @Override
    @Subscribe
    public void handleDeadlineListChangedEvent(DeadlineListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveDeadlineList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}

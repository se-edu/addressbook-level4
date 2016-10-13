package seedu.todoList.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.todoList.commons.events.model.TodoListChangedEvent;
import seedu.todoList.commons.events.model.EventListChangedEvent;
import seedu.todoList.commons.events.model.DeadlineListChangedEvent;
import seedu.todoList.commons.events.storage.DataSavingExceptionEvent;
import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.model.ReadOnlyTaskList;
import seedu.todoList.model.UserPrefs;
import seedu.todoList.storage.TaskListStorage;

/**
 * API of the Storage component
 */
public interface Storage {

	// ================ UserPrefs methods ==============================
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    
    // ================ TodoList methods ==============================
    String getTodoListFilePath();

    Optional<ReadOnlyTaskList> readTodoList() throws DataConversionException, IOException;
    
    public Optional<ReadOnlyTaskList> readTodoList(String filePath) throws DataConversionException, IOException;

    void saveTodoList(ReadOnlyTaskList todoList) throws IOException;
    
    public void saveTodoList(ReadOnlyTaskList TodoList, String filePath) throws IOException;

    /**
     * Saves the current version of the TaskList to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTodoListChangedEvent(TodoListChangedEvent abce);
    
    
    // ================ EventList methods ==============================
    String getEventListFilePath();

    Optional<ReadOnlyTaskList> readEventList() throws DataConversionException, IOException;
    
    public Optional<ReadOnlyTaskList> readEventList(String filePath) throws DataConversionException, IOException;
    
    void saveEventList(ReadOnlyTaskList eventList) throws IOException;
    
    public void saveEventList(ReadOnlyTaskList eventList, String filePath) throws IOException;

    /**
     * Saves the current version of the TaskList to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEventListChangedEvent(EventListChangedEvent abce);
    
    
    // ================ DeadlineList methods ==============================
    String getDeadlineListFilePath();

    Optional<ReadOnlyTaskList> readDeadlineList() throws DataConversionException, IOException;
    
    public Optional<ReadOnlyTaskList> readDeadlineList(String filePath) throws DataConversionException, IOException;
    
    public void saveDeadlineList(ReadOnlyTaskList taskList, String filePath) throws IOException;

    void saveDeadlineList(ReadOnlyTaskList deadlineList) throws IOException;

    /**
     * Saves the current version of the TaskList to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleDeadlineListChangedEvent(DeadlineListChangedEvent abce);
}

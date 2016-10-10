package seedu.todoList.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.todoList.commons.events.model.TodoListChangedEvent;
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
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    
    // ================ TodoList methods ==============================
    @Override
    String getTodoListFilePath();

    @Override
    Optional<ReadOnlyTaskList> readTodoList() throws DataConversionException, IOException;

    @Override
    void saveTodoList(ReadOnlyTaskList todoList) throws IOException;

    /**
     * Saves the current version of the TaskList to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTodoListChangedEvent(TodoListChangedEvent abce);
    
    
    // ================ EventList methods ==============================
    @Override
    String getEventListFilePath();

    @Override
    Optional<ReadOnlyTaskList> readEventList() throws DataConversionException, IOException;

    @Override
    void saveEventList(ReadOnlyTaskList todoList) throws IOException;

    /**
     * Saves the current version of the TaskList to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTodoListChangedEvent(EventListChangedEvent abce);
    
    
    // ================ DeadlineList methods ==============================
    @Override
    String getDeadlineListFilePath();

    @Override
    Optional<ReadOnlyTaskList> readDeadlineList() throws DataConversionException, IOException;

    @Override
    void saveDeadlineList(ReadOnlyTaskList todoList) throws IOException;

    /**
     * Saves the current version of the TaskList to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleDeadlineListChangedEvent(DeadlineListChangedEvent abce);
}

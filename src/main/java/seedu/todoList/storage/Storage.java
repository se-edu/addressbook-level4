package seedu.todoList.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.todoList.commons.events.model.TodoListChangedEvent;
import seedu.todoList.commons.events.storage.DataSavingExceptionEvent;
import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.model.ReadOnlyTodoList;
import seedu.todoList.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TodoListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTodoListFilePath();

    @Override
    Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException;

    @Override
    void saveTodoList(ReadOnlyTodoList TodoList) throws IOException;

    /**
     * Saves the current version of the Todo Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTodoListChangedEvent(TodoListChangedEvent abce);
}

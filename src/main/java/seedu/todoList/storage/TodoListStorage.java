package seedu.todoList.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.model.ReadOnlyTodoList;

/**
 * Represents a storage for {@link seedu.TaskList.model.TodoList}.
 */
public interface TodoListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTodoListFilePath();

    /**
     * Returns TodoList data as a {@link ReadOnlyTodoList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException;

    /**
     * @see #getTodoListFilePath()
     */
    Optional<ReadOnlyTodoList> readTodoList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTodoList} to the storage.
     * @param TodoList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTodoList(ReadOnlyTodoList TodoList) throws IOException;

    /**
     * @see #saveTodoList(ReadOnlyTodoList)
     */
    void saveTodoList(ReadOnlyTodoList TodoList, String filePath) throws IOException;

}

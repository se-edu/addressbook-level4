package seedu.todoList.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.model.ReadOnlyTodoList;

/**
 * Represents a storage for {@link seedu.TodoList.model.TodoList}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskListFilePath();

    /**
     * Returns TodoList data as a {@link ReadOnlyTaskList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTodoList> readTaskList() throws DataConversionException, IOException;

    /**
     * @see #getTaskListFilePath()
     */
    Optional<ReadOnlyTodoList> readTaskList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskList} to the storage.
     * @param TaskList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(ReadOnlyTaskList TaskList) throws IOException;

    /**
     * @see #saveTodoList(ReadOnlyTaskList)
     */
    void saveTaskList(ReadOnlyTaskList TaskList, String filePath) throws IOException;

}

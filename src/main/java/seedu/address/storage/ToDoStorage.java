package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyToDo;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.ToDo}.
 */
public interface ToDoStorage {

    /**
     * Returns the file path of the data file.
     */
    String getToDoFilePath();

    /**
     * Returns ToDo data as a {@link ReadOnlyToDo}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyToDo> readToDo() throws DataConversionException, IOException;

    /**
     * @see #getToDoFilePath()
     */
    Optional<ReadOnlyToDo> readToDo(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyToDo} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveToDo(ReadOnlyToDo addressBook) throws IOException;

    /**
     * @see #saveToDo(ReadOnlyToDo)
     */
    void saveToDo(ReadOnlyToDo addressBook, String filePath) throws IOException;

}

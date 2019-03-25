package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWorkoutBook;

/**
 * Represents a storage for {@link seedu.address.model.WorkoutBook}.
 */
public interface WorkoutBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWorkoutBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyWorkoutBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWorkoutBook> readWorkoutBook() throws DataConversionException, IOException;

    /**
     * @see #getWorkoutBookFilePath()
     */
    Optional<ReadOnlyWorkoutBook> readWorkoutBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWorkoutBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook) throws IOException;

    /**
     * @see #saveWorkoutBook(ReadOnlyWorkoutBook)
     */
    void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook, Path filePath) throws IOException;

}

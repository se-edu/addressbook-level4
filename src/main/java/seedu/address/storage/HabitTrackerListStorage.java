package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyHabitTrackerList;

/**
 * Represents a storage for {@link seedu.address.model.HabitTrackerList}.
 */

public interface HabitTrackerListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getHabitTrackerListFilePath();

    /**
     * Returns HabitTrackerList data as a {@link ReadOnlyHabitTrackerList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyHabitTrackerList> readHabitTrackerList() throws DataConversionException, IOException;

    /**
     * @see #getHabitTrackerListFilePath()
     */
    Optional<ReadOnlyHabitTrackerList> readHabitTrackerList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyHabitTrackerList} to the storage.
     * @param habitTrackerList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHabitTrackerList(ReadOnlyHabitTrackerList habitTrackerList) throws IOException;

    /**
     * @see #saveHabitTrackerList(ReadOnlyHabitTrackerList)
     */
    void saveHabitTrackerList(ReadOnlyHabitTrackerList habitTrackerList, Path filePath) throws IOException;
}


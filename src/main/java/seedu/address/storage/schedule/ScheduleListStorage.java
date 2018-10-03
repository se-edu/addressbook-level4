package seedu.address.storage.schedule;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.schedule.ReadOnlyScheduleList;

/**
 * Represents a storage for {@link AddressBook}.
 */
public interface ScheduleListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getScheduleListFilePath();

    /**
     * Returns ScheduleList data as a {@link ReadOnlyScheduleList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyScheduleList> readScheduleList() throws DataConversionException, IOException;

    /**
     * @see #getScheduleListFilePath()
     */
    Optional<ReadOnlyScheduleList> readScheduleList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyScheduleList} to the storage.
     * @param scheduleList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException;

    /**
     * @see #saveScheduleList(ReadOnlyScheduleList)
     */
    void saveScheduleList(ReadOnlyScheduleList scheduleList, Path filePath) throws IOException;

}

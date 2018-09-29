package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyLeaveList;

/**
 * Represents a storage for {@link seedu.address.model.LeaveList}.
 */

public interface LeaveListStorage {


    /**
     * Returns the file path of the data file.
     */
    Path getLeaveListFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyLeaveList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyLeaveList> readLeaveList() throws DataConversionException, IOException;

    /**
     * @see #getLeaveListFilePath()
     */
    Optional<ReadOnlyLeaveList> readLeaveList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyLeaveList} to the storage.
     * @param leaveList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLeaveList(ReadOnlyLeaveList leaveList) throws IOException;

    /**
     * @see #saveLeaveList(ReadOnlyLeaveList)
     */
    void saveLeaveList(ReadOnlyLeaveList leaveList, Path filePath) throws IOException;

}

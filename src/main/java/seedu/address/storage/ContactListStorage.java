package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ContactList;
import seedu.address.model.ReadOnlyContactList;

/**
 * Represents a storage for {@link ContactList}.
 */
public interface ContactListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getContactListFilePath();

    /**
     * Returns ContactList data as a {@link ReadOnlyContactList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyContactList> readContactList() throws DataConversionException, IOException;

    /**
     * @see #getContactListFilePath()
     */
    Optional<ReadOnlyContactList> readContactList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyContactList} to the storage.
     * @param contactList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveContactList(ReadOnlyContactList contactList) throws IOException;

    /**
     * @see #saveContactList(ReadOnlyContactList)
     */
    void saveContactList(ReadOnlyContactList contactList, Path filePath) throws IOException;

}

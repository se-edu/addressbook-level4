package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyExpenditureList;

/**
 * Represents a storage for {@link seedu.address.model.ExpenditureList}.
 */

public interface ExpenditureListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getExpenditureListFilePath();

    /**
     * Returns ExpenditureList data as a {@link ReadOnlyExpenditureList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyExpenditureList> readExpenditureList() throws DataConversionException, IOException;

    /**
     * @see #getExpenditureListFilePath()
     */
    Optional<ReadOnlyExpenditureList> readExpenditureList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyExpenditureList} to the storage.
     * @param expenditureList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExpenditureList(ReadOnlyExpenditureList expenditureList) throws IOException;

    /**
     * @see #saveExpenditureList(ReadOnlyExpenditureList)
     */
    void saveExpenditureList(ReadOnlyExpenditureList expenditureList, Path filePath) throws IOException;
}

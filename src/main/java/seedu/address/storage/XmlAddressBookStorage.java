package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlAddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    /**
     * Returns the address boook data in the specified location.
     *   Returns {@code Optional.empty()} if the file does not exist.
     * @param filePath cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAddressBook addressBook;

        try {
            addressBook = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        } catch (IOException e) {
            logger.warning("Error reading from AddressBook data file " + filePath + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(addressBook);
    }

    /**
     * Saves the given address book to the specified file. The file will be created if it is missing,
     *   and overwritten if it exists.
     * @param addressBook cannot be null
     * @param filePath cannot be null
     * @throws IOException if there was any I/O problem while writing to the file.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new StorageAddressBook(addressBook));
    }
}

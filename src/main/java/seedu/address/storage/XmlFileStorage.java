package seedu.address.storage;

import seedu.address.exceptions.DataConversionException;
import seedu.address.commons.XmlUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(File file, StorageAddressBook addressBook)
            throws DataConversionException, FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static StorageAddressBook loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, StorageAddressBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

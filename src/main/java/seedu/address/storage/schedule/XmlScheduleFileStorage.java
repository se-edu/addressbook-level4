package seedu.address.storage.schedule;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook data in an XML file
 */
public class XmlScheduleFileStorage {


    /**
     * Saves the given schedulelist data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableScheduleList scheduleList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, scheduleList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableScheduleList loadDataFromSaveScheduleListFile(Path file)
            throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableScheduleList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

package seedu.todoList.storage;

import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores TodoList  data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given TodoList  data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskList TodoList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, TodoList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns TodoList in the file or an empty TodoList
     */
    public static XmlSerializableTaskList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTaskList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

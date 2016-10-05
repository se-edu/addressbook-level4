package seedu.todoList.storage;

import javax.xml.bind.JAXBException;

import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.commons.util.XmlUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores Todobook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given Todobook data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTodoList TodoList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, TodoList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns Todo book in the file or an empty Todo book
     */
    public static XmlSerializableTodoList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTodoList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

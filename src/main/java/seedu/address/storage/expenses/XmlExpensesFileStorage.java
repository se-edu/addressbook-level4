package seedu.address.storage.expenses;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook data in an XML file
 */
public class XmlExpensesFileStorage {
    /**
     * Saves the given expenses list data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableExpensesList expensesList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, expensesList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns expenses list in the file or an empty expenses list
     */
    public static XmlSerializableExpensesList loadDataFromExpensesSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableExpensesList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}

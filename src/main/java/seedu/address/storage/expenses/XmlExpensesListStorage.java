package seedu.address.storage.expenses;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.expenses.ReadOnlyExpensesList;

/**
 * A class to access ExpensesList data stored as an xml file on the hard disk.
 */
public class XmlExpensesListStorage implements ExpensesListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlExpensesListStorage.class);

    private Path filePath;

    public XmlExpensesListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getExpensesListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyExpensesList> readExpensesList() throws DataConversionException, IOException {
        return readExpensesList(filePath);
    }

    /**
     * Similar to {@link #readExpensesList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyExpensesList> readExpensesList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ExpensesList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableExpensesList xmlExpensesList = XmlExpensesFileStorage.loadDataFromExpensesSaveFile(filePath);
        try {
            return Optional.of(xmlExpensesList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveExpensesList(ReadOnlyExpensesList expensesList) throws IOException {
        saveExpensesList(expensesList, filePath);
    }

    /**
     * Similar to {@link #saveExpensesList(ReadOnlyExpensesList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveExpensesList(ReadOnlyExpensesList expensesList, Path filePath) throws IOException {
        requireNonNull(expensesList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlExpensesFileStorage.saveDataToFile(filePath, new XmlSerializableExpensesList(expensesList));
    }

}

package seedu.address.storage.expenses;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.expenses.ReadOnlyExpensesList;

/**
 * Represents a storage for {@link seedu.address.model.expenses.ExpensesList}.
 */
public interface ExpensesListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getExpensesListFilePath();

    /**
     * Returns ExpensesList data as a {@link ReadOnlyExpensesList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyExpensesList> readExpensesList() throws DataConversionException, IOException;

    /**
     * @see #getExpensesListFilePath()
     */
    Optional<ReadOnlyExpensesList> readExpensesList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyExpensesList} to the storage.
     * @param expensesList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExpensesList(ReadOnlyExpensesList expensesList) throws IOException;

    /**
     * @see #saveExpensesList(ReadOnlyExpensesList)
     */
    void saveExpensesList(ReadOnlyExpensesList expensesList, Path filePath) throws IOException;

}

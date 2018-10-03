package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ExpensesListChangedEvent;
import seedu.address.commons.events.model.ScheduleListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.UserPrefs;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
import seedu.address.model.expenses.ReadOnlyExpensesList;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.storage.addressbook.AddressBookStorage;
import seedu.address.storage.expenses.ExpensesListStorage;
import seedu.address.storage.schedule.ScheduleListStorage;
import seedu.address.storage.userpref.UserPrefsStorage;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, ExpensesListStorage, UserPrefsStorage, ScheduleListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Path getExpensesListFilePath();

    @Override
    Path getScheduleListFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyExpensesList> readExpensesList() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyScheduleList> readScheduleList() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    void saveExpensesList(ReadOnlyExpensesList expensesList) throws IOException;

    @Override
    void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    /**
     * Saves the current version of the Expenses List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */

    void handleExpensesListChangedEvent(ExpensesListChangedEvent elce);

    void handleScheduleListChangedEvent(ScheduleListChangedEvent abce);
}

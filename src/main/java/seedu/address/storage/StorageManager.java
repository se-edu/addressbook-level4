package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
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
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private ExpensesListStorage expensesListStorage;
    private ScheduleListStorage scheduleListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, ExpensesListStorage expensesListStorage,
                          ScheduleListStorage scheduleListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.expensesListStorage = expensesListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.scheduleListStorage = scheduleListStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ ExpensesList methods ==============================
    @Override
    public Path getExpensesListFilePath() {
        return expensesListStorage.getExpensesListFilePath();
    }
    @Override
    public Optional<ReadOnlyExpensesList> readExpensesList() throws DataConversionException, IOException {
        return readExpensesList(expensesListStorage.getExpensesListFilePath());
    }
    @Override
    public Optional<ReadOnlyExpensesList> readExpensesList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return expensesListStorage.readExpensesList(filePath);
    }
    @Override
    public void saveExpensesList(ReadOnlyExpensesList expensesList) throws IOException {
        saveExpensesList(expensesList, expensesListStorage.getExpensesListFilePath());
    }
    @Override
    public void saveExpensesList(ReadOnlyExpensesList expensesList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        expensesListStorage.saveExpensesList(expensesList, filePath);
    }
    @Override
    @Subscribe
    public void handleExpensesListChangedEvent(ExpensesListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveExpensesList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ ScheduleList methods ==============================

    @Override
    public Path getScheduleListFilePath() {
        return scheduleListStorage.getScheduleListFilePath();
    }

    @Override
    public Optional<ReadOnlyScheduleList> readScheduleList() throws DataConversionException, IOException {
        return readScheduleList(scheduleListStorage.getScheduleListFilePath());
    }

    @Override
    public Optional<ReadOnlyScheduleList> readScheduleList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return scheduleListStorage.readScheduleList(filePath);
    }

    @Override
    public void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException {
        saveScheduleList(scheduleList, scheduleListStorage.getScheduleListFilePath());
    }

    @Override
    public void saveScheduleList(ReadOnlyScheduleList scheduleList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        scheduleListStorage.saveScheduleList(scheduleList, filePath);
    }


    @Override
    @Subscribe
    public void handleScheduleListChangedEvent(ScheduleListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));

        try {
            saveScheduleList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}

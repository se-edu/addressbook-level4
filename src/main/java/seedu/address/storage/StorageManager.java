package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyExpenditureList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private TaskListStorage taskListStorage;
    private WorkoutBookStorage workoutBookStorage;
    private ExpenditureListStorage expenditureListStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          TaskListStorage taskListStorage, ExpenditureListStorage expenditureListStorage,
                          WorkoutBookStorage workoutBookStorage) {
      super();

        this.taskListStorage = taskListStorage;
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.workoutBookStorage = workoutBookStorage;
        this.expenditureListStorage = expenditureListStorage;
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
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
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

    // ====================Task List methods ========================================

    @Override
    public Path getTaskListFilePath() {
        return taskListStorage.getTaskListFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return readTaskList(taskListStorage.getTaskListFilePath());
    }
    @Override
    public Optional<ReadOnlyTaskList> readTaskList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskListStorage.readTaskList(filePath);
    }
    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        saveTaskList(taskList, taskListStorage.getTaskListFilePath());
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskListStorage.saveTaskList(taskList, filePath);
    }

    @Override
    public Path getExpenditureListFilePath() {
        return expenditureListStorage.getExpenditureListFilePath();
    }

    @Override
    public Optional<ReadOnlyExpenditureList> readExpenditureList() throws DataConversionException, IOException {
        return readExpenditureList(expenditureListStorage.getExpenditureListFilePath());
    }

    @Override
    public Optional<ReadOnlyExpenditureList> readExpenditureList(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return expenditureListStorage.readExpenditureList(filePath);
    }

    @Override
    public void saveExpenditureList(ReadOnlyExpenditureList expenditureList) throws IOException {
        saveExpenditureList(expenditureList, expenditureListStorage.getExpenditureListFilePath());
    }

    @Override
    public void saveExpenditureList(ReadOnlyExpenditureList expenditureList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        expenditureListStorage.saveExpenditureList(expenditureList, filePath);
    }

    // =================WorkoutBook methods =====================================
    @Override
    public Path getWorkoutBookFilePath() {
        return workoutBookStorage.getWorkoutBookFilePath();
    }

    @Override
    public Optional<ReadOnlyWorkoutBook> readWorkoutBook() throws DataConversionException, IOException {
        return readWorkoutBook(workoutBookStorage.getWorkoutBookFilePath());
    }
    @Override
    public Optional<ReadOnlyWorkoutBook> readWorkoutBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return workoutBookStorage.readWorkoutBook(filePath);

    }
    @Override
    public void saveWorkoutBook(ReadOnlyWorkoutBook workoutList) throws IOException {
        saveWorkoutBook(workoutList, workoutBookStorage.getWorkoutBookFilePath());
    }
    @Override
    public void saveWorkoutBook(ReadOnlyWorkoutBook workoutList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        workoutBookStorage.saveWorkoutBook(workoutList, filePath);

    }

}

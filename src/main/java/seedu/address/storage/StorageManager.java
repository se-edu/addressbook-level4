package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyContactList;
import seedu.address.model.ReadOnlyExpenditureList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of ContactList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ContactListStorage contactListStorage;
    private UserPrefsStorage userPrefsStorage;
    private TaskListStorage taskListStorage;
    private TickedTaskListStorage tickedTaskListStorage;
    private WorkoutBookStorage workoutBookStorage;
    private ExpenditureListStorage expenditureListStorage;


    public StorageManager(ContactListStorage contactListStorage, UserPrefsStorage userPrefsStorage,
                          TaskListStorage taskListStorage, ExpenditureListStorage expenditureListStorage,
                          WorkoutBookStorage workoutBookStorage, TickedTaskListStorage tickedTaskListStorage) {
      super();

        this.taskListStorage = taskListStorage;
        this.tickedTaskListStorage = tickedTaskListStorage;
        this.contactListStorage = contactListStorage;
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


    // ================ ContactList methods ==============================

    @Override
    public Path getContactListFilePath() {
        return contactListStorage.getContactListFilePath();
    }

    @Override
    public Optional<ReadOnlyContactList> readContactList() throws DataConversionException, IOException {
        return readContactList(contactListStorage.getContactListFilePath());
    }

    @Override
    public Optional<ReadOnlyContactList> readContactList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return contactListStorage.readContactList(filePath);
    }

    @Override
    public void saveContactList(ReadOnlyContactList contactList) throws IOException {
        saveContactList(contactList, contactListStorage.getContactListFilePath());
    }

    @Override
    public void saveContactList(ReadOnlyContactList contactList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        contactListStorage.saveContactList(contactList, filePath);
    }

    // ====================task List methods ========================================

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
        logger.info("READING FROM FILE" + filePath);
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
    public Path getTickedTaskListFilePath() {
        return tickedTaskListStorage.getTickedTaskListFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskList> readTickedTaskList() throws DataConversionException, IOException {
        logger.info("HELLO");
        return readTickedTaskList(tickedTaskListStorage.getTickedTaskListFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskList> readTickedTaskList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        logger.info("READING FROM FILE" + filePath);
        return tickedTaskListStorage.readTickedTaskList(filePath);
    }

    @Override
    public void saveTickedTaskList(ReadOnlyTaskList tasklist) throws IOException {

    }

    @Override
    public void saveTickedTaskList(ReadOnlyTaskList taskList, Path filepath) throws IOException {

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

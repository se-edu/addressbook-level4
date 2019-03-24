package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.ReadOnlyExpenditureList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.ReadOnlyWorkoutBook;

/**
 * API of the Storage component
 */

public interface Storage extends AddressBookStorage, UserPrefsStorage, TaskListStorage, WorkoutBookStorage, ExpenditureListStorage {


    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    /* -----------------------Address Book ---------------------------------------------*/
    @Override
    Path getAddressBookFilePath();


    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /* -----------------------Task List ---------------------------------------------*/

    @Override
    Path getTaskListFilePath();

    @Override
    Optional<ReadOnlyTaskList> readTaskList(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveTaskList (ReadOnlyTaskList taskList) throws IOException;
    /* ----------------------Workout Book ----------------------------------------------*/

    @Override
    Path getWorkoutBookFilePath();

    @Override
    Optional<ReadOnlyWorkoutBook> readWorkoutBook(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook) throws IOException;


    /* -----------------------Expenditure List ---------------------------------------------*/

    @Override
    Path getExpenditureListFilePath();


    @Override
    Optional<ReadOnlyExpenditureList> readExpenditureList() throws DataConversionException, IOException;

    @Override
    void saveExpenditureList(ReadOnlyExpenditureList expenditureList) throws IOException;

}

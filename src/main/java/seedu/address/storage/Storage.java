package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyContactList;
import seedu.address.model.ReadOnlyExpenditureList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ContactListStorage, UserPrefsStorage,
        TaskListStorage, WorkoutBookStorage, ExpenditureListStorage, TickedTaskListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    /* -----------------------Contact List ---------------------------------------------*/
    @Override
    Path getContactListFilePath();


    @Override
    Optional<ReadOnlyContactList> readContactList() throws DataConversionException, IOException;

    @Override
    void saveContactList(ReadOnlyContactList contactList) throws IOException;

    /* -----------------------task List ---------------------------------------------*/

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

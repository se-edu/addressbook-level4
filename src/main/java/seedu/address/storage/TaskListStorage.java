package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.model.ReadOnlyTaskList;

public interface TaskListStorage {
    Path getTaskListFilePath();

    Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException;
    Optional<ReadOnlyTaskList> readTaskList(Path filePath) throws DataConversionException, IOException;
    void saveTaskList(ReadOnlyTaskList taskList) throws IOException;
    void saveTaskList(ReadOnlyTaskList taskList, Path filePath) throws IOException;
}


package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskList;

/**
 * Represents a storage for ticked task list.
 */
public interface TickedTaskListStorage {

    Path getTickedTaskListFilePath();

    Optional<ReadOnlyTaskList> readTickedTaskList() throws DataConversionException, IOException;
    Optional<ReadOnlyTaskList> readTickedTaskList(Path filePath) throws DataConversionException, IOException;
    void saveTickedTaskList(ReadOnlyTaskList tasklist) throws IOException;
    void saveTickedTaskList(ReadOnlyTaskList taskList, Path filepath) throws IOException;
}

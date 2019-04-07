package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTaskList;

/**
 * A class to access Task List Data as a json file on the hard disk
 */
public class JsonTaskListStorage implements TaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);
    private Path filePath;

    public JsonTaskListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTaskListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException {
        return readTaskList(filePath);
    }

    /**
     * Similar to {@link #readTaskList()}
     * @param filePath location of path, cannot be null
     * @throws DataConversionException if file not in correct format
     */
    public Optional<ReadOnlyTaskList> readTaskList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonSerializableTaskList> jsonTaskList = JsonUtil.readJsonFile(filePath,
                JsonSerializableTaskList.class);

        if (!jsonTaskList.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonTaskList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        saveTaskList(taskList, filePath);
    }

    /**
     * Similar to {@link #saveTaskList(ReadOnlyTaskList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTaskList(ReadOnlyTaskList taskList, Path filePath) throws IOException {
        requireNonNull(taskList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaskList(taskList), filePath);
    }


}

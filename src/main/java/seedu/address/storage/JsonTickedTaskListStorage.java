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
 * A class to access to the Ticked task List Data as a json file on the hard disk
 */
public class JsonTickedTaskListStorage implements TickedTaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTickedTaskListStorage.class);
    private Path filePath;

    public JsonTickedTaskListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTickedTaskListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskList> readTickedTaskList() throws DataConversionException {
        return readTickedTaskList(filePath);
    }

    /**
     * Similar to {@link #readTickedTaskList()}
     * @param filePath location of path, cannot be null
     * @throws DataConversionException if file not in correct format
     */
    public Optional<ReadOnlyTaskList> readTickedTaskList(Path filePath) throws DataConversionException {
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
    public void saveTickedTaskList(ReadOnlyTaskList taskList) throws IOException {
        saveTickedTaskList(taskList, filePath);
    }

    /**
     * Similar to {@link #saveTickedTaskList(ReadOnlyTaskList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTickedTaskList(ReadOnlyTaskList taskList, Path filePath) throws IOException {
        requireNonNull(taskList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaskList(taskList), filePath);
    }


}

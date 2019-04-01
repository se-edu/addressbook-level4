package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyHabitTrackerList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A class to access HabitTrackerList data stored as json file on the hard disk.
 */
public class JsonHabitTrackerListStorage implements HabitTrackerListStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonHabitTrackerListStorage.class);

    private Path filePath;

    public JsonHabitTrackerListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHabitTrackerListStorage() {
        return filePath;
    }

    @Override
    public Path getHabitTrackerListFilePath() {
        return null;
    }

    @Override
    public Optional<ReadOnlyHabitTrackerList> readHabitTrackerList() throws DataConversionException {
        return readHabitTrackerList(filePath);
    }

    /**
     * Similar to {@link #readHabitTrackerList()} .
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyHabitTrackerList> readHabitTrackerList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableHabitTrackerList> jsonHabitTrackerList = JsonUtil.readJsonFile(
                filePath, JsonSerializableHabitTrackerList.class);
        if (!jsonHabitTrackerList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHabitTrackerList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveHabitTrackerList(ReadOnlyHabitTrackerList habitTrackerList) throws IOException {
        saveHabitTrackerList(habitTrackerList, filePath);
    }

    /**
     * Similar to {@link #saveHabitTrackerList(ReadOnlyHabitTrackerList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHabitTrackerList(ReadOnlyHabitTrackerList habitTrackerList, Path filePath) throws IOException {
        requireNonNull(habitTrackerList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHabitTrackerList(habitTrackerList), filePath);
    }
}

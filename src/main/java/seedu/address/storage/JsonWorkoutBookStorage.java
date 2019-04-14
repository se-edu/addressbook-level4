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
import seedu.address.model.ReadOnlyWorkoutBook;

/**
 * A class to access WorkoutList data stored as a json file on the hard disk.
 */
public class JsonWorkoutBookStorage implements WorkoutBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWorkoutBookStorage.class);

    private Path filePath;

    public JsonWorkoutBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWorkoutBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWorkoutBook> readWorkoutBook() throws DataConversionException {
        return readWorkoutBook(filePath);
    }

    /**
     * Similar to {@link #readWorkoutBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWorkoutBook> readWorkoutBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableWorkoutBook> jsonWorkoutBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableWorkoutBook.class);
        if (!jsonWorkoutBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWorkoutBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook)throws IOException {
        saveWorkoutBook(workoutBook, filePath);
    }

    /**
     * Similar to {@link #saveWorkoutBook(ReadOnlyWorkoutBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook, Path filePath) throws IOException {
        requireNonNull(workoutBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWorkoutBook(workoutBook), filePath);
    }

}

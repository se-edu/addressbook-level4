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
import seedu.address.model.ReadOnlyExpenditureList;

/**
 * A class to access ExpenditureList data stored as a json file on the hard disk.
 */

public class JsonExpenditureListStorage implements ExpenditureListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonExpenditureListStorage.class);

    private Path filePath;

    public JsonExpenditureListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getExpenditureListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyExpenditureList> readExpenditureList() throws DataConversionException {
        return readExpenditureList(filePath);
    }

    /**
     * Similar to {@link #readExpenditureList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyExpenditureList> readExpenditureList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableExpenditureList> jsonExpenditureList = JsonUtil.readJsonFile(
                filePath, JsonSerializableExpenditureList.class);
        if (!jsonExpenditureList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonExpenditureList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveExpenditureList(ReadOnlyExpenditureList expenditureList) throws IOException {
        saveExpenditureList(expenditureList, filePath);
    }

    /**
     * Similar to {@link #saveExpenditureList(ReadOnlyExpenditureList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveExpenditureList(ReadOnlyExpenditureList expenditureList, Path filePath) throws IOException {
        requireNonNull(expenditureList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableExpenditureList(expenditureList), filePath);
    }

}

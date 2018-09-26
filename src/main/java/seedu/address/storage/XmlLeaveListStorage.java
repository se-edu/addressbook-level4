package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyLeaveList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class XmlLeaveListStorage implements LeaveListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlLeaveListStorage.class);

    private Path filePath;

    public XmlLeaveListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLeaveListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLeaveList> readLeaveList() throws DataConversionException, IOException {
        return readLeaveList(filePath);
    }

    /**
     * Similar to {@link #readLeaveList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyLeaveList> readLeaveList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("LeaveList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableLeaveList xmlLeaveList= XmlFileStorage.loadDataFromSaveFile2(filePath);
        try {
            return Optional.of(xmlLeaveList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveLeaveList(ReadOnlyLeaveList leaveList) throws IOException {
        saveLeaveList(leaveList, filePath);
    }

    /**
     * Similar to {@link #saveLeaveList(ReadOnlyLeaveList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveLeaveList(ReadOnlyLeaveList leaveList, Path filePath) throws IOException {
        requireNonNull(leaveList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableLeaveList(leaveList));
    }

}

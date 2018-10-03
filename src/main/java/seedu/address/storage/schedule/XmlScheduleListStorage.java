package seedu.address.storage.schedule;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.schedule.ReadOnlyScheduleList;

/**
 * A class to access ScheduleList data stored as an xml file on the hard disk.
 */
public class XmlScheduleListStorage implements ScheduleListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlScheduleListStorage.class);

    private Path filePath;

    public XmlScheduleListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getScheduleListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyScheduleList> readScheduleList() throws DataConversionException, IOException {
        return readScheduleList(filePath);
    }

    /**
     * Similar to {@link #readScheduleList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyScheduleList> readScheduleList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ScheduleList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableScheduleList xmlScheduleList = XmlScheduleFileStorage.loadDataFromSaveScheduleListFile(filePath);
        try {
            return Optional.of(xmlScheduleList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException {
        saveScheduleList(scheduleList, filePath);
    }

    /**
     * Similar to {@link #saveScheduleList(ReadOnlyScheduleList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveScheduleList(ReadOnlyScheduleList scheduleList, Path filePath) throws IOException {
        requireNonNull(scheduleList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlScheduleFileStorage.saveDataToFile(filePath, new XmlSerializableScheduleList(scheduleList));
    }

}

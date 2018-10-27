package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTranscript;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlTranscriptStorage implements TranscriptStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTranscriptStorage.class);

    private Path filePath;

    public XmlTranscriptStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTranscriptFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTranscript> readTranscript() throws DataConversionException, IOException {
        return readTranscript(filePath);
    }

    /**
     * Similar to {@link #readTranscript()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTranscript> readTranscriptk(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Transcript file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableTranscript xmlTranscript = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlTranscript.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTranscript(ReadOnlyTranscript transcript) throws IOException {
        saveTranscript(transcript, filePath);
    }

    /**
     * Similar to {@link #saveTranscript(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTranscript(ReadOnlyTranscript transcript, Path filePath) throws IOException {
        requireNonNull(transcript);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableTranscript(transcript));
    }

}

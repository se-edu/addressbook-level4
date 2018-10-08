package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTranscript;
import seedu.address.model.Transcript;

/**
 * A class to access Transcript stored in the hard disk as a json file
 */
public class JsonTranscriptStorage implements TranscriptStorage {

    private final Path filePath;

    public JsonTranscriptStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTranscriptFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTranscript> readTranscript() throws DataConversionException {
        return readTranscript(filePath);
    }

    /**
     * Similar to {@link #readTranscript()}
     *
     * @param transcriptFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<ReadOnlyTranscript> readTranscript(Path transcriptFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(transcriptFilePath, ReadOnlyTranscript.class);
    }

    @Override
    public void saveTranscript(ReadOnlyTranscript transcript) throws IOException {
        JsonUtil.saveJsonFile(new Transcript(transcript), filePath);
    }

    @Override
    public void saveTranscript(ReadOnlyTranscript transcript, Path filePath) throws IOException {
        requireNonNull(transcript);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new Transcript(transcript), filePath);
    }

}

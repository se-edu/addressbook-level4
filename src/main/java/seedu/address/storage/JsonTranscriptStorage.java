package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Transcript;

/**
 * A class to access Transcript stored in the hard disk as a json file
 */
public class JsonTranscriptStorage implements TranscriptStorage {

    private Path filePath;

    public JsonTranscriptStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTranscriptFilePath() {
        return filePath;
    }
    
    @Override
    public Optional<Transcript> readTranscript() throws DataConversionException {
        return readTranscript(filePath);
    }

    /**
     * Similar to {@link #readTranscript()}
     * @param transcriptFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Transcript> readTranscript(Path transcriptFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(transcriptFilePath, Transcript.class);
    }

    @Override
    public void saveTranscript(Transcript transcript) throws IOException {
        JsonUtil.saveJsonFile(transcript, filePath);
    }

    @Override
    public void saveTranscript(Transcript transcript, Path filePath) throws IOException {
        JsonUtil.saveJsonFile(transcript, filePath);
    }

}

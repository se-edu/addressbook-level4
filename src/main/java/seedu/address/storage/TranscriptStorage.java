package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;

import seedu.address.model.ReadOnlyTranscript;

/**
 * Represents a storage for {@link seedu.address.model.Transcript}.
 */
public interface TranscriptStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTranscriptFilePath();

    /**
     * Returns Transcript data as a {@link ReadOnlyTranscript}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTranscript> readTranscript() throws DataConversionException, IOException;

    /**
     * @see #getTranscriptFilePath()
     */
    Optional<ReadOnlyTranscript> readTranscript(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTranscript} to the storage.
     *
     * @param transcript cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTranscript(ReadOnlyTranscript transcript) throws IOException;

    /**
     * @see #saveTranscript(ReadOnlyTranscript)
     */
    void saveTranscript(ReadOnlyTranscript transcript, Path filePath) throws IOException;

}

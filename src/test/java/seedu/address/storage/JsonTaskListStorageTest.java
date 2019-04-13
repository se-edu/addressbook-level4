package seedu.address.storage;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.TaskList;


public class JsonTaskListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTaskListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTaskList(null);
    }

    private java.util.Optional<ReadOnlyTaskList> readTaskList(String filePath) throws Exception {
        return new JsonTaskListStorage(Paths.get(filePath)).readTaskList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskList("NonExistentFile.json").isPresent());
    }

    @Test
    public void saveTaskList_nullTaskList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTaskList(null, "SomeFile.json");
    }

    /**
     * Saves {@code tasklist} at the specified {@code filePath}.
     */
    private void saveTaskList(ReadOnlyTaskList taskList, String filePath) {
        try {
            new JsonTaskListStorage(Paths.get(filePath))
                    .saveTaskList(taskList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTaskList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTaskList(new TaskList(), null);
    }
}

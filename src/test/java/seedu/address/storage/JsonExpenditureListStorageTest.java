package seedu.address.storage;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.ExpenditureList;
import seedu.address.model.ReadOnlyExpenditureList;

public class JsonExpenditureListStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonExpenditureListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readExpenditureList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readExpenditureList(null);
    }

    private java.util.Optional<ReadOnlyExpenditureList> readExpenditureList(String filePath) throws Exception {
        return new JsonExpenditureListStorage(Paths.get(filePath)).readExpenditureList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readExpenditureList("NonExistentFile.json").isPresent());
    }

    @Test
    public void saveExpenditureList_nullExpenditureList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveExpenditureList(null, "SomeFile.json");
    }

    /**
     * Saves {@code expenditurelist} at the specified {@code filePath}.
     */
    private void saveExpenditureList(ReadOnlyExpenditureList expenditureList, String filePath) {
        try {
            new JsonExpenditureListStorage(Paths.get(filePath))
                    .saveExpenditureList(expenditureList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveExpenditureList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveExpenditureList(new ExpenditureList(), null);
    }
}

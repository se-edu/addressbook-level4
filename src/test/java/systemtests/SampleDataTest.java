package systemtests;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

public class SampleDataTest extends AddressBookSystemTest {

    @TempDir
    public Path tempDir;

    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected AddressBook getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = tempDir.resolve("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Person[] expectedList = SampleDataUtil.getSamplePersons();
        assertListMatching(getPersonListPanel(), expectedList);
    }
}

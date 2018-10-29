package systemtests;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//import org.junit.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.module.Module;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends AddressBookSystemTest {
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
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
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

    //TODO: REMOVE
    //@Test
    //public void addressBook_dataFileDoesNotExist_loadSampleData() {
    //Person[] expectedList = SampleDataUtil.getSamplePersons();
    //assertListMatching(getPersonListPanel(), expectedList);
    //}

    /**
     * Tests if the file at {@code filePath} can load sample data.
     */
    public void transcriptDataFileDoesNotExistLoadSampleData() {
        Module[] expectedModule = SampleDataUtil.getSampleModules();
        assertListMatching(getModuleListPanel(), expectedModule);
    }

}

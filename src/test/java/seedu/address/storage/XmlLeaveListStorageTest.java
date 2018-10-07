package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalLeave.REQUEST_2;
import static seedu.address.testutil.TypicalLeave.REQUEST_3;
import static seedu.address.testutil.TypicalLeave.REQUEST_4;
import static seedu.address.testutil.TypicalLeave.getTypicalLeaveList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.LeaveList;
import seedu.address.model.ReadOnlyLeaveList;

public class XmlLeaveListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlLeaveListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readLeaveList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readLeaveList(null);
    }

    private java.util.Optional<ReadOnlyLeaveList> readLeaveList(String filePath) throws Exception {
        return new XmlLeaveListStorage (Paths.get(filePath)).readLeaveList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readLeaveList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readLeaveList("NotXmlFormatLeaveList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveLeaveList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        LeaveList original = getTypicalLeaveList();
        XmlLeaveListStorage xmlLeaveListStorage = new XmlLeaveListStorage(filePath);

        //Save in new file and read back
        xmlLeaveListStorage.saveLeaveList(original, filePath);
        ReadOnlyLeaveList readBack = xmlLeaveListStorage.readLeaveList(filePath).get();
        assertEquals(original, new LeaveList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addRequest(REQUEST_3);
        original.removeRequest(REQUEST_2);
        xmlLeaveListStorage.saveLeaveList(original, filePath);
        readBack = xmlLeaveListStorage.readLeaveList(filePath).get();
        assertEquals(original, new LeaveList(readBack));

        //Save and read without specifying file path
        original.addRequest(REQUEST_4);
        xmlLeaveListStorage.saveLeaveList(original); //file path not specified
        readBack = xmlLeaveListStorage.readLeaveList().get(); //file path not specified
        assertEquals(original, new LeaveList(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLeaveList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code leaveList} at the specified {@code filePath}.
     */
    private void saveLeaveList(ReadOnlyLeaveList leaveList, String filePath) {
        try {
            new XmlLeaveListStorage(Paths.get(filePath))
                    .saveLeaveList(leaveList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveLeaveList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLeaveList(new LeaveList(), null);
    }

}

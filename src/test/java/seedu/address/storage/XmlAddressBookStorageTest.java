package seedu.address.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ToDo;
import seedu.address.model.task.Task;
import seedu.address.model.ReadOnlyToDo;
import seedu.address.testutil.TypicalTestTasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlAddressBookStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyToDo> readAddressBook(String filePath) throws Exception {
        return new XmlAddressBookStorage(filePath).readToDo(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        ToDo original = td.getTypicalToDo();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveToDo(original, filePath);
        ReadOnlyToDo readBack = xmlAddressBookStorage.readToDo(filePath).get();
        assertEquals(original, new ToDo(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.hoon));
        original.removePerson(new Task(TypicalTestTasks.alice));
        xmlAddressBookStorage.saveToDo(original, filePath);
        readBack = xmlAddressBookStorage.readToDo(filePath).get();
        assertEquals(original, new ToDo(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.ida));
        xmlAddressBookStorage.saveToDo(original); //file path not specified
        readBack = xmlAddressBookStorage.readToDo().get(); //file path not specified
        assertEquals(original, new ToDo(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyToDo addressBook, String filePath) throws IOException {
        new XmlAddressBookStorage(filePath).saveToDo(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(new ToDo(), null);
    }


}

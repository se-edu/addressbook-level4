package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ContactList;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableContactListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableContactListTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsContactList.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonContactList.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonContactList.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableContactList dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableContactList.class).get();
        ContactList contactListFromFile = dataFromFile.toModelType();
        ContactList typicalPersonsContactList = TypicalPersons.getTypicalContactList();
        assertEquals(contactListFromFile, typicalPersonsContactList);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableContactList dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableContactList.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableContactList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableContactList.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableContactList.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}

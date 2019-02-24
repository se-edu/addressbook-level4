package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.testutil.SerializableTestClass;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {

    @TempDir
    public Path tempDir;

    private Path serialisationFile;

    @BeforeEach
    public void setUp() {
        serialisationFile = tempDir.resolve("serialize.json");
    }

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        JsonUtil.serializeObjectToJsonFile(serialisationFile, serializableTestClass);

        assertEquals(FileUtil.readFromFile(serialisationFile), SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(serialisationFile, SerializableTestClass.JSON_STRING_REPRESENTATION);

        SerializableTestClass serializableTestClass = JsonUtil
                .deserializeObjectFromJsonFile(serialisationFile, SerializableTestClass.class);

        assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
    }

    //TODO: @Test jsonUtil_readJsonStringToObjectInstance_correctObject()

    //TODO: @Test jsonUtil_writeThenReadObjectToJson_correctObject()
}

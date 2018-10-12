package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalModules.getTranscriptWithModules;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTranscript;
import seedu.address.model.Transcript;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.util.ModuleBuilder;

public class JsonTranscriptStorageTest {
    public static final Module DISCRETE_MATH = new ModuleBuilder().withCode("CS1231")
            .withYear(1)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();
    public static final Module PROGRAMMING_METHODOLOGY_TWO = new ModuleBuilder().withCode("CS2030")
            .withYear(2)
            .withSemester(Semester.SEMESTER_TWO)
            .withCredit(4)
            .withGrade("B")
            .build();
    public static final Module DATA_STRUCTURES = new ModuleBuilder().withCode("CS2040")
            .withYear(3)
            .withSemester(Semester.SEMESTER_SPECIAL_ONE)
            .withCredit(4)
            .withGrade("F")
            .build();
    public static final Module SOFTWARE_ENGINEERING = new ModuleBuilder().withCode("CS2103")
            .withYear(3)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();
    public static final Module DATABASE_SYSTEMS = new ModuleBuilder().withCode("CS2102")
            .withYear(2)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();
    public static final Module DATABASE_SYSTEMS_2MC = new ModuleBuilder().withCode("CS2102")
            .withYear(2)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(2)
            .withGrade("A+")
            .build();

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTranscriptStorageTest");
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTranscript_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTranscript(null);
    }

    private Optional<ReadOnlyTranscript> readTranscript(String filePath) throws DataConversionException {
        return new JsonTranscriptStorage(Paths.get(filePath)).readTranscript(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String transcriptFileInTestDataFolder) {
        return transcriptFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(transcriptFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTranscript("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_emptyTranscript_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTranscript("EmptyTranscript.json");
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTranscript("NotJsonFormatTranscript.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readTranscript_invalidModuleTranscript_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTranscript("InvalidTranscript.json");
    }

    @Test
    public void readTranscript_invalidAndValidModuleTranscript_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTranscript("InvalidAndValidTranscript.json");
    }

    @Test
    public void readTranscript_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        Transcript expected = getTranscriptWithModules(
                DATABASE_SYSTEMS, DISCRETE_MATH, SOFTWARE_ENGINEERING, PROGRAMMING_METHODOLOGY_TWO);
        Transcript actual = new Transcript(readTranscript("ExtraValuesTranscript.json").get());
        assertEquals(expected, actual);
    }

    @Test
    public void readTranscript_typicalTranscript_noException() throws DataConversionException {
        Transcript typical = new Transcript(readTranscript("TypicalTranscript.json").get());
    }


    @Test
    public void readAndSaveTranscript_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempTranscript.json");
        Transcript original = getTranscriptWithModules(DISCRETE_MATH, PROGRAMMING_METHODOLOGY_TWO, DATA_STRUCTURES);
        JsonTranscriptStorage jsonTranscriptStorage = new JsonTranscriptStorage(filePath);

        //Save in new file and read back
        jsonTranscriptStorage.saveTranscript(original, filePath);
        ReadOnlyTranscript readBack = jsonTranscriptStorage.readTranscript(filePath).get();
        assertEquals(original, new Transcript(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addModule(SOFTWARE_ENGINEERING);
        original.removeModule(DISCRETE_MATH);
        jsonTranscriptStorage.saveTranscript(original, filePath);
        readBack = jsonTranscriptStorage.readTranscript(filePath).get();
        assertEquals(original, new Transcript(readBack));

        //Save and read without specifying file path
        original.addModule(DATABASE_SYSTEMS);
        jsonTranscriptStorage.saveTranscript(original); //file path not specified
        readBack = jsonTranscriptStorage.readTranscript().get(); //file path not specified
        assertEquals(original, new Transcript(readBack));
    }

    @Test
    public void saveTranscript_nullTranscript_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTranscript(null, "SomeFile.json");
    }

    /**
     * Saves {@code Transcript} at the specified {@code filePath}.
     */
    private void saveTranscript(ReadOnlyTranscript transcript, String filePath) {
        try {
            new JsonTranscriptStorage(Paths.get(filePath))
                    .saveTranscript(transcript, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTranscript_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTranscript(new Transcript(), null);
    }


}

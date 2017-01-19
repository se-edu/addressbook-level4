package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import javafx.fxml.FXML;
import seedu.address.MainApp;

public class UiPartTest {

    private static final String MISSING_FILE_PATH = "UiPartTest/missingFile.fxml";
    private static final String INVALID_FILE_PATH = "UiPartTest/invalidFile.fxml";
    private static final String VALID_FILE_PATH = "UiPartTest/validFile.fxml";
    private static final TestFxmlObject VALID_FILE_ROOT = new TestFxmlObject("Hello World!");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void constructor_nullFileUrl_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>((URL) null);
    }

    @Test
    public void constructor_missingFileUrl_throwsAssertionError() throws Exception {
        URL missingFileUrl = new URL(testFolder.getRoot().toURI().toURL(), MISSING_FILE_PATH);
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>(missingFileUrl);
    }

    @Test
    public void constructor_invalidFileUrl_throwsAssertionError() {
        URL invalidFileUrl = getTestFileUrl(INVALID_FILE_PATH);
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>(invalidFileUrl);
    }

    @Test
    public void constructor_validFileUrl_loadsFile() {
        URL validFileUrl = getTestFileUrl(VALID_FILE_PATH);
        assertEquals(VALID_FILE_ROOT, new TestUiPart<TestFxmlObject>(validFileUrl).getRoot());
    }

    @Test
    public void constructor_nullFileName_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>((String) null);
    }

    @Test
    public void constructor_missingFileName_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>(MISSING_FILE_PATH);
    }

    @Test
    public void constructor_invalidFileName_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        new TestUiPart<Object>(INVALID_FILE_PATH);
    }

    private URL getTestFileUrl(String testFilePath) {
        String testFilePathInView = "/view/" + testFilePath;
        URL testFileUrl = MainApp.class.getResource(testFilePathInView);
        assertNotNull(testFilePathInView + " does not exist.", testFileUrl);
        return testFileUrl;
    }

    /**
     * UiPart used for testing.
     * It should only be used with invalid FXML files or the valid file located at {@link VALID_FILE_PATH}.
     */
    private static class TestUiPart<T> extends UiPart<T> {

        @FXML
        private TestFxmlObject validFileRoot; // Check that @FXML annotations work

        TestUiPart(URL fxmlFileUrl) {
            super(fxmlFileUrl);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }

        TestUiPart(String fxmlFileName) {
            super(fxmlFileName);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }

    }

}

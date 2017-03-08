package seedu.address.testutil;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;

/**
 * Utility class for temporary files created during testing.
 */
public class TemporaryFilesUtil {

    /**
     * Folder used for temporary files created during testing. Ignored by Git.
     */
    public static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    /**
     * Appends the {@code fileName} to the sandbox folder path.
     * Creates the sandbox folder if it does not exist.
     * @return the sandbox folder file path appended with given file name.
     */
    public static String getFilePathOfFileInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    /**
     * Saves the {@code data} to a file at {@code filePath}.
     * Creates the file at {@code filePath} if it doesn't exist.
     */
    public static <T> void save(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}

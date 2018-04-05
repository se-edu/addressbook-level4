package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path filePath) {
        return Files.exists(filePath) && Files.isRegularFile(filePath);
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            Files.createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist.
     */
    public static void createFile(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path filePath) throws IOException {
        return new String(Files.readAllBytes(filePath), CHARSET);
    }


    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path filePath, String content) throws IOException {
        Files.write(filePath, content.getBytes(CHARSET));
    }

}

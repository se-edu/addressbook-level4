package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path filePath) {
        return Files.exists(filePath) && Files.isRegularFile(filePath);
    }

    /**
     * Checks if the path given is valid.
     * @param path A String representing the file path.
     * @return     true if path is valid, false if invalid.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path filePath) throws IOException {
        if (!isFileExists(filePath)) {
            createFile(filePath);
        }
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path filePath) throws IOException {
        Path parentDir = filePath.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            createParentDirsOfFile(filePath);
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

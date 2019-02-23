package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * A set of helper methods to manage temporary directories.
 */
public class DirectoryInitUtil {

    /**
     * Initialise a temporary directory by copying all files from {@code sourceDir} to {@code tempDir}.
     */
    public static void initializeTemporaryDirectory(Path sourceDir, Path tempDir) throws IOException {
        Files
            .walk(sourceDir)
            .forEach(filePath -> copyFilesToTempDir(filePath, sourceDir, tempDir));
    }

    /**
     * Helper method to copy a file located at {@code filePath} to {@code tempDir}. Files copied maintain
     * relative directory structure with reference to {@code sourceDir}.
     */
    private static void copyFilesToTempDir(Path filePath, Path sourceDir, Path tempDir) {
        Path relativePath = relativizeToTempDir(filePath, sourceDir, tempDir);
        try {
            Files.copy(filePath, relativePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Given an {@code origin} folder and a {@code filePath} within the folder, as well as a
     * {@code destination} folder, calculates the relative location if {@code filePath} were to
     * be copied to the {@code destination} folder while maintaining the same directory structure.
     */
    private static Path relativizeToTempDir(Path filePath, Path origin, Path destination) {
        return destination.resolve(origin.relativize(filePath));
    }

    /**
     * Creates a temporary file with a randomized filename prefixed by {@code name} in the {@code tempDir}.
     */
    public static Path createTemporaryFileInFolder(Path tempDir, String name) throws IOException {
        return Files.createTempFile(tempDir, name, "");
    }

    /**
     * Creates a temporary file with a randomized filename in the {@code tempDir}
     */
    public static Path createTemporaryFileInFolder(Path tempDir) throws IOException {
        return createTemporaryFileInFolder(tempDir, "");
    }
}

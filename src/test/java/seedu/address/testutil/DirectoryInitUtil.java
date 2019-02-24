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
     * Files copied will mirror the directory structure of {@code sourceDir}.
     */
    public static void initializeTemporaryDirectory(Path sourceDir, Path tempDir) throws IOException {
        Files
            .walk(sourceDir)
            .forEach(filePath -> copyFileToTempDir(filePath, sourceDir, tempDir));
    }

    /**
     * Helper method to copy a file located at {@code filePath} to {@code tempDir}, mirroring the directory structure
     * of {@code sourceDir}.
     */
    private static void copyFileToTempDir(Path filePath, Path sourceDir, Path tempDir) {
        Path newPath = calculateNewPath(filePath, sourceDir, tempDir);
        try {
            Files.copy(filePath, newPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new AssertionError("Failed to copy " + filePath + " from source directory " + sourceDir
                    + " to temporary directory " + tempDir + ". Error: " + e.getMessage());
        }
    }

    /**
     * Given an {@code origin} folder and a {@code filePath} within the folder, returns the path of
     * the new file if {@code filePath} were to be copied to the {@code destination} folder
     * while maintaining the same directory structure.
     */
    private static Path calculateNewPath(Path filePath, Path origin, Path destination) {
        Path relativePath = origin.relativize(filePath);
        return destination.resolve(relativePath);
    }

    /**
     * Creates a temporary file with a randomized filename prefixed by {@code prefix} in the {@code tempDir}.
     */
    public static Path createTemporaryFileInFolder(Path tempDir, String prefix) throws IOException {
        return Files.createTempFile(tempDir, prefix, "");
    }

    /**
     * Creates a temporary file with a randomized filename in the {@code tempDir}
     */
    public static Path createTemporaryFileInFolder(Path tempDir) throws IOException {
        return createTemporaryFileInFolder(tempDir, "");
    }
}

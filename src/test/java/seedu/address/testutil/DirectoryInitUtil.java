package seedu.address.testutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A set of helper methods to manage temporary directories.
 */
public class DirectoryInitUtil {

    /**
     * Initialise a temporary directory by copying all files from {@code sourceDir} to {@code tempDir}.
     * Files copied will mirror the directory structure of {@code sourceDir}.
     */
    public static void initializeTemporaryDirectory(Path sourceDir, Path tempDir) {
        copyRecursively(sourceDir.toFile(), tempDir.toFile());
    }

    /**
     * Helper method to copy a {@code source} file to a {@code target} file.
     * If {@code source} is a directory, all files in {@code source} will be copied recursively to {@code target}.
     */
    private static void copyRecursively(File source, File target) {
        if (source.isDirectory()) {
            copyDirectoryRecursively(source, target);
        } else {
            copyFile(source, target);
        }
    }

    /**
     * Helper method to recursively copy all contents of a {@code source} directory to a {@code target} directory.
     * The {@code target} directory file structure will mirror that of the {@code source} directory.
     */
    private static void copyDirectoryRecursively(File source, File target) {
        target.mkdir();
        for (String child: source.list()) {
            copyRecursively(new File(source, child), new File(target, child));
        }
    }

    /**
     * Helper method to copy a single file located from {@code source} to {@code target}.
     * This is a wrapper method over Files#copy, to throw an AssertionError instead of an IOException so that
     * calling methods do not need to repeatedly check for IOException.
     */
    private static void copyFile(File source, File target) {
        try {
            Files.copy(source.toPath(), target.toPath());
        } catch (IOException ioe) {
            throw new AssertionError("Failed to copy file from " + source.getPath()
                + " to " + target.getPath() + ". Error: " + ioe.getMessage());
        }
    }

    /**
     * Creates a temporary file with a randomized filename prefixed by {@code prefix} in the {@code tempDir}.
     */
    public static Path createTemporaryFileInFolder(Path tempDir, String prefix) {
        try {
            return Files.createTempFile(tempDir, prefix, "");
        } catch (IOException e) {
            throw new AssertionError("Failed to create temporary file in directory " + tempDir
                + ". Error:" + e.getMessage());
        }
    }

    /**
     * Creates a temporary file with a randomized filename in the {@code tempDir}
     */
    public static Path createTemporaryFileInFolder(Path tempDir) {
        return createTemporaryFileInFolder(tempDir, "");
    }
}

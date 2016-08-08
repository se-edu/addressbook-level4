package commons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes and reads file
 */
public class FileUtil {
    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(File file) {
        return file.exists() && file.isFile();
    }

    public static boolean isFileExists(String filepath) {
        return isFileExists(new File(filepath));
    }

    public static boolean isDirExists(File dir) {
        return dir.exists() && dir.isDirectory();
    }

    public static void createIfMissing(File file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories
     *
     * @return true if file is created, false if file already exists
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return false;
        }

        createParentDirsOfFile(file);

        return file.createNewFile();
    }

    public static void deleteFile(String filepath) throws IOException {
        deleteFile(new File(filepath));
    }

    public static void deleteFile(File file) throws IOException {
        Files.delete(file.toPath());
    }

    public static void deleteFileIfExists(String filepath) throws IOException {
        deleteFileIfExists(new File(filepath));
    }

    public static void deleteFileIfExists(File file) throws IOException {
        if (!isFileExists(file)) {
            return;
        }

        deleteFile(file);
    }

    /**
     * Lists all files in directory and its subdirectories
     */
    public static List<File> listFilesInDir(Path directory) {
        List<File> filepaths = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path path : stream) {
                if (path.toFile().isDirectory()) {
                    filepaths.addAll(listFilesInDir(path));
                } else {
                    filepaths.add(path.toFile());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filepaths;
    }

    /**
     * Creates the given directory along with its parent directories
     *
     * @param dir the directory to be created; assumed not null
     * @throws IOException if the directory or a parent directory cannot be created
     */
    public static void createDirs(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to make directories of " + dir.getName());
        }
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(File file) throws IOException {
        File parentDir = file.getParentFile();

        if (parentDir != null) {
            createDirs(parentDir);
        }
    }

    /**
     * Move file from source to dest
     * @param isOverwrite set true to overwrite source
     */
    public static void moveFile(Path source, Path dest, boolean isOverwrite) throws IOException {
        if (isOverwrite) {
            Files.move(source, dest, StandardCopyOption.REPLACE_EXISTING);
        } else {
            Files.move(source, dest);
        }
    }

    /**
     *
     * @param source
     * @param dest
     * @param isOverwrite
     * @throws IOException
     */
    public static void copyFile(Path source, Path dest, boolean isOverwrite) throws IOException {
        if (isOverwrite) {
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        } else {
            Files.copy(source, dest);
        }
    }

    /**
     * @return List of filenames failed to be moved
     */
    public static List<String> moveContentOfADirectoryToAnother(String sourceDir, String targetDir) {
        List<File> sourceFiles = FileUtil.listFilesInDir(Paths.get(sourceDir));
        List<String> failedToMoveFiles = new ArrayList<>();

        for (File sourceFile : sourceFiles) {
            Path sourceFilePath = sourceFile.toPath();
            Path targetFilePath = Paths.get(targetDir + File.separator + sourceFile.getName());

            try {
                FileUtil.moveFile(sourceFilePath, targetFilePath, true);
            } catch (IOException e) {
                e.printStackTrace();
                failedToMoveFiles.add(sourceFile.getPath());
            }
        }

        return failedToMoveFiles;
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), CHARSET);
    }

    /**
     * Writes the content of a stream into a file
     *
     * Overwrites any existing file found at filePath
     * @param in
     * @param filePath
     * @throws IOException
     */
    public static void writeStreamIntoFile(InputStream in, Path filePath) throws IOException {
        Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(CHARSET));
    }

    public static String readFromInputStream(InputStream inputStream) {
        java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static File getJarFileOfClass(Class givenClass) throws URISyntaxException {
        return new File(givenClass.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
    }

    /**
     * Converts a string to a platform-specific file path
     * @param pathWithForwardSlash A String representing a file path but using '/' as the separator
     * @return {@code pathWithForwardSlash} but '/' replaced with {@code File.separator}
     */
    public static String getPath(String pathWithForwardSlash) {
        assert pathWithForwardSlash != null;
        assert pathWithForwardSlash.contains("/");
        return pathWithForwardSlash.replace("/", File.separator);
    }

    /**
     * Gets the file name from the given file path, assuming that
     * path components are '/'-separated
     *
     * @param filePath should not be null
     * @return
     */
    public static String getFileName(String filePath) {
        String[] pathComponents = filePath.split("/");
        return pathComponents[pathComponents.length - 1];
    }

    public static <T> void serializeObjectToJsonFile(File jsonFile, T objectToSerialize) throws IOException {
        FileUtil.writeToFile(jsonFile, JsonUtil.toJsonString(objectToSerialize));
    }

    public static <T> T deserializeObjectFromJsonFile(File jsonFile, Class<T> classOfObjectToDeserialize)
            throws IOException {
        return JsonUtil.fromJsonString(FileUtil.readFromFile(jsonFile), classOfObjectToDeserialize);
    }
}

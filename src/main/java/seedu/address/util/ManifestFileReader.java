package seedu.address.util;

import seedu.address.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Reads values in manifest file
 */
public class ManifestFileReader {
    private static final AppLogger logger = LoggerManager.getLogger(ManifestFileReader.class);

    private static String getResourcePath() {
        Class mainAppClass = MainApp.class;
        String className = mainAppClass.getSimpleName() + ".class";
        return mainAppClass.getResource(className).toString();
    }

    public static boolean isRunFromJar() {
        String resourcePath = getResourcePath();
        return resourcePath.startsWith("jar");

    }

    private static String getManifestPath() {
        String resourcePath = getResourcePath();

        return resourcePath.substring(0, resourcePath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";
    }

    private static Optional<Manifest> getManifest() {
        if (!isRunFromJar()) {
            return Optional.empty();
        }

        String manifestPath = getManifestPath();

        Manifest manifest;

        try {
            manifest = new Manifest(new URL(manifestPath).openStream());
        } catch (IOException e) {
            logger.debug("Manifest can't be read, most probably not run from JAR", e);
            return Optional.empty();
        }

        return Optional.of(manifest);
    }

    /**
     * @return the libraries as mentioned in manifest classpath
     */
    public static Optional<List<String>> getLibrariesInClasspathFromManifest() {
        Optional<Manifest> manifest = getManifest();

        if (!manifest.isPresent()) {
            logger.debug("Manifest is not present");

            return Optional.of(new ArrayList<>());
        }

        Attributes attr = manifest.get().getMainAttributes();

        return Optional.of(new ArrayList<>(Arrays.asList(attr.getValue("Class-path").split("\\s+"))));
    }
}

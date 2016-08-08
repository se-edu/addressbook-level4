package seedu.address.util;

import seedu.address.MainApp;
import seedu.address.exceptions.DependencyCheckException;
import seedu.address.commons.FileUtil;
import seedu.address.commons.JsonUtil;
import seedu.address.commons.VersionData;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Checks if all required dependencies are present
 */
public class DependencyChecker {

    private static final AppLogger logger = LoggerManager.getLogger(DependencyChecker.class);
    private final String requiredJavaVersionString;
    private Runnable quitApp;

    public  DependencyChecker(String requiredJavaVersionString, Runnable quitApp) {
        this.requiredJavaVersionString = requiredJavaVersionString;
        this.quitApp = quitApp;
    }

    public void verify() {
        if (!ManifestFileReader.isRunFromJar()) {
            logger.info("Not run from Jar, skipping dependencies check");
            return;
        }
        logger.info("Verifying dependencies");

        try {
            checkJavaVersionDependency(requiredJavaVersionString);
        } catch (DependencyCheckException e) {
            showErrorDialogAndQuit("Java Version Check Failed", "There are missing dependencies", e.getMessage());
        }

        try {
            checkLibrariesDependency();
        } catch (DependencyCheckException e) {
            showErrorDialogAndQuit("Libraries Check Failed", "Your Java version is not compatible", e.getMessage());
        }

        logger.info("All dependencies present");
    }

    public void checkJavaVersionDependency(String javaVersion) throws DependencyCheckException {
        logger.info("Verifying java version dependency");

        JavaVersion requiredVersion;
        try {
            requiredVersion = JavaVersion.fromString(javaVersion);
        } catch (IllegalArgumentException e) {
            logger.warn("Required Java Version string cannot be parsed. This should have been covered by test.");
            assert false;
            return;
        }

        JavaVersion runtimeVersion;
        String javaRuntimeVersionString = System.getProperty("java.runtime.version");
        try {
            runtimeVersion = JavaVersion.fromString(javaRuntimeVersionString);
        } catch (IllegalArgumentException e) {
            throw new DependencyCheckException(String.format(
                    "Java runtime version (%s) is not known and may not be compatible with this app.",
                    javaRuntimeVersionString));
        }

        if (JavaVersion.isJavaVersionLower(runtimeVersion, requiredVersion)) {
            throw new DependencyCheckException(String.format(
                    "Your Java Version (%s) is lower than this app's requirement (%s). Please update your Java.",
                    runtimeVersion, requiredVersion));
        }
    }

    public void checkLibrariesDependency() throws DependencyCheckException {
        logger.info("Verifying dependency libraries are present");

        List<String> missingDependencies = getMissingDependencies();

        if (!missingDependencies.isEmpty()) {
            StringBuilder message = new StringBuilder("Missing dependencies:\n");
            for (String missingDependency : missingDependencies) {
                message.append("- ").append(missingDependency).append("\n");
            }
            String missingDependenciesMessage = message.toString().trim();
            logger.warn(missingDependenciesMessage);

            throw new DependencyCheckException(missingDependenciesMessage);
        }
    }

    public List<String> getMissingDependencies() {
        Optional<List<String>> dependenciesWrapper = ManifestFileReader.getLibrariesInClasspathFromManifest();

        if (!dependenciesWrapper.isPresent()) {
            logger.info("Dependencies not present - not running check as this indicates not run from JAR");
            return new ArrayList<>();
        }

        List<String> dependencies = dependenciesWrapper.get();

        excludePlatformSpecificDependencies(dependencies);

        return dependencies.stream()
                .filter(dependency -> !FileUtil.isFileExists(dependency)).collect(Collectors.toList());
    }

    private void excludePlatformSpecificDependencies(List<String> dependencies) {
        String json = FileUtil.readFromInputStream(MainApp.class.getResourceAsStream("/VersionData.json"));

        seedu.address.commons.VersionData versionData;

        try {
            versionData = JsonUtil.fromJsonString(json, VersionData.class);
        } catch (IOException e) {
            logger.warn("Failed to parse JSON data to process platform specific dependencies", e);
            return;
        }

        List<String> librariesNotForCurrentMachine =  versionData.getLibraries().stream()
                .filter(libDesc -> libDesc.getOs() != seedu.address.commons.OsDetector.Os.ANY
                                    && libDesc.getOs() != seedu.address.commons.OsDetector.getOs())
                .map(libDesc -> "lib/" + libDesc.getFileName())
                .collect(Collectors.toList());

        dependencies.removeAll(librariesNotForCurrentMachine);
    }

    public void showErrorDialogAndQuit(String title, String headerText, String contentText) {
        JOptionPane.showMessageDialog(null, headerText + "\n\n" + contentText, title, JOptionPane.ERROR_MESSAGE);
        quitApp.run();
    }
}

package seedu.address.commons.core;

import java.io.File;

import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

/**
 * Provides helper methods related to Gradle.
 */
public class Gradle {
    /**
     * Builds the resources folder.
     */
    public static void buildResources() {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File("."));
        ProjectConnection connection = connector.connect();
        BuildLauncher buildLauncher = connection.newBuild();
        buildLauncher.forTasks("processResources");
        buildLauncher.run();
    }
}

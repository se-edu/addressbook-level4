package seedu.address;

/**
 * The main entry point to the application.
 *
 * Addressbook is unable to run in a jdk11 environment using MainApp class
 * directly and it gives an error of below:
 *
 *     Error: JavaFX runtime components are missing, and are required to run this application
 *
 * This error comes from sun.launcher.LauncherHelper in the java.base
 * module. The reason for this is that the Main app extends Application
 * and has a main method. If that is the case, the LauncherHelper will
 * check for the javafx.graphics module to be present as a named module.
 * If that module is not present, the launch is aborted.
 *
 * One simple workaround is to have a separate main class that doesn't
 * extend Application. Hence it doesn't do the check on javafx.graphics
 * module, and when the required jars are on the classpath, it works fine.
 */
public class Main {
    public static void main(String[] args) {
        MainApp.initialize(args);
    }
}

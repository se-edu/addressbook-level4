package seedu.address;

import javafx.application.Application;

/**
 * The main entry point to the application.
 *
 * This is a workaround for the following error when MainApp is made the
 * entry point of the application:
 *     Error: JavaFX runtime components are missing, and are required to run this application
 *
 * The reason is that MainApp extends Application. In that case, the
 * LauncherHelper will check for the javafx.graphics module to be present
 * as a named module. We don't use JavaFX via the module system so it can't
 * find the javafx.graphics module, and so the launch is aborted. Hence,
 * adding the javafx runtime dependency is not enough in this case.
 *
 * This is more like a JDK 11 problem which cannot be solved elegantly.
 * Workaround this by having a separate main class (Main) that doesn't
 * extend Application to be the new entry point of addressbook.
 */
public class Main {
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}

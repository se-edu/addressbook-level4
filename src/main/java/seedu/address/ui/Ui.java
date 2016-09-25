package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.MainApp;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

    /** Returns the main stage. */
    Stage getPrimaryStage();
}

package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.MainApp;

/**
 * Created by dcsdcr on 25/9/2016.
 */
public interface Ui {
    void start(Stage primaryStage, MainApp mainApp);

    void stop();

    /**
     * Returns the main stage.
     */
    Stage getPrimaryStage();
}

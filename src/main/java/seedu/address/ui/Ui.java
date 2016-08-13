package seedu.address.ui;

import seedu.address.MainApp;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.util.Config;
import seedu.address.util.GuiSettings;
import javafx.stage.Stage;

/**
 * The UI of the app.
 */
public class Ui {
    private seedu.address.controller.Ui ui;
    private UserPrefs pref;

    public Ui(MainApp mainApp, ModelManager modelManager, Config config, UserPrefs pref){
        ui = new seedu.address.controller.Ui(mainApp, modelManager, config, pref);
        this.pref = pref;
    }

    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public void stop() {
        Stage stage = ui.getPrimaryStage();
        GuiSettings guiSettings = new GuiSettings(stage.getWidth(), stage.getHeight(),
                                                  (int) stage.getX(), (int) stage.getY());
        pref.setGuiSettings(guiSettings);
        ui.stop();
    }
}

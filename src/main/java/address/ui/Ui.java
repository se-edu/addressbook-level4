package address.ui;

import address.MainApp;
import address.controller.MainController;
import address.model.ModelManager;
import address.model.UserPrefs;
import address.util.Config;
import address.util.GuiSettings;
import javafx.stage.Stage;

/**
 * The UI of the app.
 */
public class Ui {
    private MainController mainController;
    private UserPrefs pref;

    public Ui(MainApp mainApp, ModelManager modelManager, Config config, UserPrefs pref){
        mainController = new MainController(mainApp, modelManager, config, pref);
        this.pref = pref;
    }

    public void start(Stage primaryStage) {
        mainController.start(primaryStage);
    }

    public void stop() {
        Stage stage = mainController.getPrimaryStage();
        GuiSettings guiSettings = new GuiSettings(stage.getWidth(), stage.getHeight(),
                                                  (int) stage.getX(), (int) stage.getY());
        pref.setGuiSettings(guiSettings);
        mainController.stop();
    }
}

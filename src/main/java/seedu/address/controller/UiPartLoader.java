package seedu.address.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.StringUtil;

import java.io.IOException;

/**
 * A utility class to load UiParts from FXML files.
 */
public class UiPartLoader {
    private final static String FXML_FILE_FOLDER = "/view/";

    public static <T extends UiPart> T loadUiPart(Stage primaryStage, T controllerSeed) {
        return loadUiPart(primaryStage, null, controllerSeed);
    }

    /**
     * Returns the controller class for a specific UI Part.
     *
     * @param primaryStage The primary stage for the view.
     * @param placeholder The placeholder where the loaded Ui Part is added.
     * @param sampleUiPart The sample of the expected UiPart class.
     * @param <T> The type of the UiPart
     * @return
     */
    public static <T extends UiPart> T loadUiPart(Stage primaryStage, AnchorPane placeholder, T sampleUiPart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(FXML_FILE_FOLDER + sampleUiPart.getFxmlPath()));
        Node mainNode = loadLoader(loader, sampleUiPart.getFxmlPath());
        UiPart controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setPlaceholder(placeholder);
        controller.setNode(mainNode);
        return (T)controller;
    }


    private static Node loadLoader(FXMLLoader loader, String fxmlFileName) {
        try {
            return loader.load();
        } catch (Exception e) {
            String errorMessage = "FXML Load Error for " + fxmlFileName;
            throw new RuntimeException(errorMessage, e);
        }
    }

}

package seedu.address.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import seedu.address.MainApp;

/**
 * A utility class to load UiParts from FXML files.
 */
public class UiPartLoader {
    private static final String FXML_FILE_FOLDER = "/view/";

    /**
     * Returns the ui class for a specific UI Part.
     *
     * @param sampleUiPart The sample of the expected UiPart class.
     * @param <T> The type of the UiPart
     */
    @Deprecated
    public static <T extends UiPart> T loadUiPart(T sampleUiPart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(FXML_FILE_FOLDER + sampleUiPart.getFxmlPath()));
        Node mainNode = loadLoader(loader, sampleUiPart.getFxmlPath());
        T controller = loader.getController();
        controller.setNode(mainNode);
        return controller;
    }

    /**
     * Returns the ui class for a specific UI Part.
     *
     * @param placeholder The placeholder where the loaded Ui Part is added.
     * @param sampleUiPart The sample of the expected UiPart class.
     * @param <T> The type of the UiPart
     */
    @Deprecated
    public static <T extends UiPart> T loadUiPart(AnchorPane placeholder, T sampleUiPart) {
        T controller = loadUiPart(sampleUiPart);
        controller.setPlaceholder(placeholder);
        return controller;
    }

    /**
     * Initializes the FXML scene graph of the provided UI Part.
     *
     * @param seedUiPart The UiPart object to be used as the ui.
     * @param <T> The type of the UiPart
     */
    @Deprecated
    public static <T extends UiPart> T initUiPart(T seedUiPart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(FXML_FILE_FOLDER + seedUiPart.getFxmlPath()));
        loader.setController(seedUiPart);
        loadLoader(loader, seedUiPart.getFxmlPath());
        return seedUiPart;
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

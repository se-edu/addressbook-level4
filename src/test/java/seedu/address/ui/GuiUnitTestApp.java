package seedu.address.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * A dummy application for unit testing of a single GUI component.
 *
 * Instead of creating the entire interface (like {@link seedu.address.TestApp}),
 * {@link GuiUnitTestApp} creates a blank window, and allow insertion of a
 * single UiPart. This allows the GUI component to be isolated from other
 * GUI components for unit testing purposes.
 */
public class GuiUnitTestApp extends Application {

    private static final int UI_UPDATE_SLEEP_DELAY = 1000;

    private final int initialSceneWidth;
    private final int initialSceneHeight;

    private Stage stage;
    private AnchorPane mainPane;

    public GuiUnitTestApp(int sceneWidth, int sceneHeight) {
        this.initialSceneWidth = sceneWidth;
        this.initialSceneHeight = sceneHeight;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.mainPane = new AnchorPane();

        Scene scene = new Scene(mainPane, initialSceneWidth, initialSceneHeight);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds a new UI part that is being tested into the scene.
     */
    public void addUiPart(UiPart<Region> part) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainPane.getChildren().add(part.getRoot());
            }
        });

        try {
            // give time for UI addition to take effect
            Thread.sleep(UI_UPDATE_SLEEP_DELAY);
        } catch (InterruptedException ie) {

        }
    }

    /**
     * Clears all UI parts added to the scene.
     */
    public void clearUiParts() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainPane.getChildren().clear();
            }
        });

        try {
            // give time for UI addition to take effect
            Thread.sleep(UI_UPDATE_SLEEP_DELAY);
        } catch (InterruptedException ie) {

        }
    }

    public Stage getStage() {
        return stage;
    }
}

package seedu.address.ui;

import guitests.GuiRobot;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * A dummy application for unit testing of a single GUI component.
 */
public class GuiUnitTestApp extends Application {

    private static final int UI_UPDATE_SLEEP_DELAY = 1000;

    private final int initialSceneWidth;
    private final int initialSceneHeight;
    private final GuiRobot guiRobot;

    private Stage stage;
    private AnchorPane mainPane;

    public GuiUnitTestApp(GuiRobot guiRobot, int sceneWidth, int sceneHeight) {
        this.guiRobot = guiRobot;
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

        // give time for UI addition to take effect
        guiRobot.sleep(UI_UPDATE_SLEEP_DELAY);
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

        // give time for UI clearing to take effect
        guiRobot.sleep(UI_UPDATE_SLEEP_DELAY);
    }

    public GuiRobot getGuiRobot() {
        return guiRobot;
    }

    public Stage getStage() {
        return stage;
    }
}

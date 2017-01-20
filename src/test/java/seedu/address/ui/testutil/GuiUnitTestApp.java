package seedu.address.ui.testutil;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.ui.UiPart;

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

        // give time for UI addition to take effect
        sleep(UI_UPDATE_SLEEP_DELAY);

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

        // give time for UI addition to take effect
        sleep(UI_UPDATE_SLEEP_DELAY);
    }

    public Stage getStage() {
        return stage;
    }

    /**
     * Sleeps this thread, allowing JavaFx threads to have a chance to run.
     */
    private void sleep(int sleepDelayInMillis) {
        try {
            Thread.sleep(sleepDelayInMillis);
        } catch (InterruptedException exception) {
            /*
             * Since sleep() must be called intentionally for JavaFx
             * threads to have a chance to run, its failure will cause
             * that to not happen at all, which is undesired behavior.
             */
            throw new RuntimeException(exception);
        }
    }
}

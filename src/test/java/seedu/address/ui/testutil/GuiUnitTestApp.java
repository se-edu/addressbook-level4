package seedu.address.ui.testutil;

import static org.junit.Assert.fail;

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
 * Instead of creating the entire GUI (like {@link seedu.address.TestApp}),
 * {@link GuiUnitTestApp} creates a blank window, and allows insertion of a
 * single UiPart. This allows the GUI part under test to be created without
 * creating the rest of the GUI.
 */
public class GuiUnitTestApp extends Application {

    private static final int DEFAULT_STAGE_WIDTH = 400;
    private static final int DEFAULT_STAGE_HEIGHT = 400;

    private Stage stage;
    private AnchorPane mainPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.mainPane = new AnchorPane();

        Scene scene = new Scene(mainPane, DEFAULT_STAGE_WIDTH, DEFAULT_STAGE_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds a new UI part that is being tested into the scene.
     *
     * This method blocks the main thread as it will have to wait
     * for the JavaFx's thread to add the UI part to the application UI,
     * otherwise the addition is not guaranteed to be completed when the
     * method exits.
     */
    public void addUiPart(UiPart<Region> part) throws InterruptedException {
        runAndWait(() -> mainPane.getChildren().add(part.getRoot()));
    }

    /**
     * Clears all UI parts added to the scene.
     *
     * This method blocks the main thread as it will have to wait
     * for the JavaFx's thread to clear the UI parts, otherwise the
     * clearing is not guaranteed to be completed when the method exits.
     */
    public void clearUiParts() throws InterruptedException {
        runAndWait(() -> mainPane.getChildren().clear());
    }

    /**
     * Runs the runnable on the JavaFx's thread, and wait for the
     * runnable to finish on that thread before we continue executing.
     */
    public void runAndWait(Runnable runnable) throws InterruptedException {
        synchronized (runnable) {
            Platform.runLater(() -> {
                synchronized (runnable) {
                    runnable.run();
                    runnable.notifyAll();
                }
            });

            runnable.wait();
        }
    }

    public void setStageWidth(int desiredWidth) {
        this.stage.setWidth(desiredWidth);
    }

    public void setStageHeight(int desiredHeight) {
        this.stage.setHeight(desiredHeight);
    }

    public Stage getStage() {
        return stage;
    }

    /**
     * Creates a {@link GuiUnitTestApp} with the stage assigned to it.
     */
    public static GuiUnitTestApp spawnApp(Stage stage) {
        try {
            GuiUnitTestApp testApp = new GuiUnitTestApp();
            testApp.start(stage);
            return testApp;

        } catch (Exception e) {
            fail("Unable to launch application.");
            return null;
        }
    }
}

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
 * {@link GuiUnitTestApp} creates a blank window, and allows insertion of a
 * single UiPart. This allows the GUI component to be isolated from other
 * GUI components for unit testing purposes.
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
     */
    public void addUiPart(UiPart<Region> part) {
        Platform.runLater(() -> mainPane.getChildren().add(part.getRoot()));
    }

    /**
     * Clears all UI parts added to the scene.
     */
    public void clearUiParts() {
        Platform.runLater(() -> mainPane.getChildren().clear());
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
}

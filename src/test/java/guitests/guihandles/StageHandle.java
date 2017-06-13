package guitests.guihandles;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import guitests.GuiRobot;
import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Provides access to a stage in a JavaFx application for GUI testing purposes.
 */
public abstract class StageHandle {
    protected final GuiRobot guiRobot = new GuiRobot();
    protected final Logger logger = LogsCenter.getLogger(this.getClass());

    private final Stage stage;

    public StageHandle(Stage stage) {
        this.stage = requireNonNull(stage);
    }

    /**
     * Closes {@code stage}.
     */
    public void closeStage() {
        guiRobot.targetWindow(stage);
        guiRobot.interact(() -> stage.close());
        guiRobot.pauseForHuman();
    }

    /**
     * Focuses on this {@code stage}.
     */
    public void focusOnStage() {
        String windowTitle = stage.getTitle();
        logger.info("Focusing on" + windowTitle);
        guiRobot.targetWindow(stage);
        guiRobot.interact(() -> stage.requestFocus());
        logger.info("Finishing focus on" + windowTitle);
    }

    /**
     * Retrieves the {@code query} node owned by the {@code stage}.
     * Throws {@code NodeNotFoundException} if no such node exist.
     */
    protected <T extends Node> T getChildNode(String query) {
        T node = guiRobot.from(stage.getScene().getRoot()).lookup(query).query();

        if (node == null) {
            throw new NodeNotFoundException();
        }

        return node;
    }
}

package guitests.guihandles;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;
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
    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage stage;

    public StageHandle(Stage stage) {
        this.stage = requireNonNull(stage);
    }

    /**
     * Closes {@code stage}.
     */
    public void close() {
        guiRobot.interact(stage::close);
        assertFalse(stage.isShowing());
    }

    /**
     * Focuses on this {@code stage}.
     */
    public void focus() {
        String windowTitle = stage.getTitle();
        logger.info("Focusing on" + windowTitle);
        guiRobot.interact(stage::requestFocus);
        logger.info("Finishing focus on" + windowTitle);
    }

    /**
     * Returns true if currently focusing on this stage.
     */
    public boolean isFocused() {
        return stage.isFocused();
    }

    /**
     * Retrieves the {@code query} node owned by the {@code stage}.
     *
     * @param query name of the CSS selector for the node to retrieve.
     * @throws NodeNotFoundException if no such node exists.
     */
    protected <T extends Node> T getChildNode(String query) {
        Optional<T> node = guiRobot.from(stage.getScene().getRoot()).lookup(query).tryQuery();
        return node.orElseThrow(NodeNotFoundException::new);
    }
}

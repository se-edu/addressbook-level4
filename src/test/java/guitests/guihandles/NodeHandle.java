package guitests.guihandles;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.logging.Logger;

import guitests.GuiRobot;
import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import seedu.address.commons.core.LogsCenter;

/**
 * Provides access to a node in a JavaFx application for GUI testing purposes.
 */
public abstract class NodeHandle {
    protected final GuiRobot guiRobot = new GuiRobot();
    protected final Logger logger = LogsCenter.getLogger(this.getClass());

    private final Node rootNode;

    protected NodeHandle(Node rootNode) {
        this.rootNode = requireNonNull(rootNode);
    }

    protected Node getRootNode() {
        return rootNode;
    }

    /**
     * Retrieves the {@code query} node owned by the {@code rootNode}.
     *
     * @param query name of the CSS selector for the node to retrieve.
     * @throws NodeNotFoundException if no such node exist.
     */
    protected <T extends Node> T getChildNode(String query) {
        Optional<T> node = Optional.ofNullable(guiRobot.from(rootNode).lookup(query).query());
        return node.orElseThrow(NodeNotFoundException::new);
    }

    /**
     * Clicks on the root node (i.e. itself).
     */
    public void click() {
        guiRobot.clickOn(rootNode);
    }
}

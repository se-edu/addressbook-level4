package guitests.guihandles;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import guitests.GuiRobot;
import javafx.scene.Node;
import seedu.address.commons.core.LogsCenter;

/**
 * Provides access to a node in a JavaFx application for GUI testing purposes.
 */
public abstract class NodeHandle {
    protected final GuiRobot guiRobot = new GuiRobot();
    protected final Logger logger = LogsCenter.getLogger(this.getClass());

    private final Node rootNode;

    public NodeHandle(Node rootNode) {
        this.rootNode = requireNonNull(rootNode);
    }

    public Node getRootNode() {
        return rootNode;
    }

    protected <T extends Node> Node getNode(String query) {
        return guiRobot.from(rootNode).lookup(query).query();
    }

    public void click() {
        guiRobot.clickOn(rootNode);
    }
}

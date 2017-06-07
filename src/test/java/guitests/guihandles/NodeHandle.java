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
    protected static final GuiRobot GUI_ROBOT = GuiRobot.getInstance();
    protected final Logger logger = LogsCenter.getLogger(this.getClass());

    private final Node rootNode;

    protected NodeHandle(Node rootNode) {
        this.rootNode = requireNonNull(rootNode);
    }

    protected Node getNode() {
        return rootNode;
    }

    protected <T extends Node> Node getChildNode(String query) {
        return GUI_ROBOT.from(rootNode).lookup(query).query();
    }

    /**
     * Clicks on the root node (i.e. itself).
     */
    public void clickOnSelf() {
        GUI_ROBOT.clickOn(rootNode);
    }
}

package guitests.guihandles;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.address.commons.core.LogsCenter;

/**
 * Provides access to a node in a JavaFx application for GUI testing purposes.
 */
public abstract class WindowHandle {
    protected final GuiRobot guiRobot = new GuiRobot();
    protected final Logger logger = LogsCenter.getLogger(this.getClass());

    private Window window;

    public WindowHandle(Window window) {
        this.window = requireNonNull(window);
    }

    public void closeWindow() {
        guiRobot.targetWindow(window);
        guiRobot.interact(() -> ((Stage) window).close());
        guiRobot.pauseForHuman(500);
    }

    public void focusOnWindow() {
        String windowTitle = ((Stage) window).getTitle();
        logger.info("Focusing " + windowTitle);
        guiRobot.targetWindow(window);
        guiRobot.interact(() -> window.requestFocus());
        logger.info("Finishing focus " + windowTitle);
    }

    protected <T extends Node> T getNode(String query) {
        return guiRobot.from(window.getScene().getRoot()).lookup(query).query();
    }
}

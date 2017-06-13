package guitests.guihandles;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.logging.Logger;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.address.commons.core.LogsCenter;

/**
 * Provides access to a window in a JavaFx application for GUI testing purposes.
 */
public abstract class WindowHandle {
    protected static final GuiRobot GUI_ROBOT = new GuiRobot();
    protected final Logger logger = LogsCenter.getLogger(this.getClass());

    private Window window;

    public WindowHandle(Window window) {
        this.window = requireNonNull(window);
    }

    /**
     * Closes {@code window}.
     */
    public void closeWindow() {
        GUI_ROBOT.targetWindow(window);
        GUI_ROBOT.interact(() -> ((Stage) window).close());
        GUI_ROBOT.pauseForHuman();
    }

    /**
     * Focuses on this {@code window}.
     */
    public void focusOnWindow() {
        String windowTitle = ((Stage) window).getTitle();
        logger.info("Focusing on" + windowTitle);
        GUI_ROBOT.targetWindow(window);
        GUI_ROBOT.interact(() -> window.requestFocus());
        logger.info("Finishing focus on" + windowTitle);
    }

    protected static Optional<Window> findWindow(String windowTitle) {
        return GUI_ROBOT.listTargetWindows().stream().filter(w -> w instanceof Stage
                    && ((Stage) w).getTitle().equals(windowTitle)).findAny();

    }

    protected <T extends Node> T getChildNode(String query) {
        return GUI_ROBOT.from(window.getScene().getRoot()).lookup(query).query();
    }
}

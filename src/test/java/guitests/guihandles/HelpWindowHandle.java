package guitests.guihandles;

import static org.junit.Assert.assertFalse;

import java.util.Optional;

import javafx.stage.Window;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HelpWindowHandle extends WindowHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    public HelpWindowHandle() {
        super(GUI_ROBOT.window(HELP_WINDOW_TITLE));
    }

    /**
     * Returns whether a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        Optional<Window> window = findWindow(HelpWindowHandle.HELP_WINDOW_TITLE);
        return window.isPresent();
    }

    /**
     * Closes the help window.
     */
    public void closeWindow() {
        super.closeWindow();
        GUI_ROBOT.pauseForHuman();

        assertFalse(isWindowPresent());
    }
}

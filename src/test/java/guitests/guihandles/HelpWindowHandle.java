package guitests.guihandles;

import static org.junit.Assert.assertFalse;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HelpWindowHandle extends WindowHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    public HelpWindowHandle() {
        super(GUI_ROBOT.window(HELP_WINDOW_TITLE));
    }

    /**
     * Closes the help window.
     */
    public void closeWindow() {
        super.closeWindow();

        assertFalse(GUI_ROBOT.isWindowActive(HELP_WINDOW_TITLE));
    }
}

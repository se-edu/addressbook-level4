package guitests.guihandles;

import static org.junit.Assert.assertFalse;

import guitests.GuiRobot;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HelpWindowHandle extends StageHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    public HelpWindowHandle() {
        super(new GuiRobot().getStage(HELP_WINDOW_TITLE).get());
    }

    /**
     * Returns whether a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().getStage(HELP_WINDOW_TITLE).isPresent();
    }

    /**
     * Closes the help window.
     */
    public void closeWindow() {
        super.closeStage();
        guiRobot.pauseForHuman();

        assertFalse(isWindowPresent());
    }
}

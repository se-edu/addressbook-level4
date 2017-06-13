package guitests.guihandles;

import static org.junit.Assert.assertFalse;

import guitests.GuiRobot;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HelpWindowHandle extends StageHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    public HelpWindowHandle() {
        super(new GuiRobot().getStage(HELP_WINDOW_TITLE));
    }

    /**
     * Closes the help window.
     */
    public void closeWindow() {
        super.close();
        guiRobot.pauseForHuman();

        assertFalse(guiRobot.isWindowShown(HELP_WINDOW_TITLE));
    }
}

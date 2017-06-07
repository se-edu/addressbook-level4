package guitests.guihandles;

import guitests.GuiRobot;
/**
 * Provides a handle to the help window of the app.
 */
public class HelpWindowHandle extends WindowHandle {

    public static final String HELP_WINDOW_TITLE = "Help";
    private static final String HELP_WINDOW_ROOT_FIELD_ID = "#helpWindowRoot";

    public HelpWindowHandle() {
        super(new GuiRobot().window(HELP_WINDOW_TITLE));
        guiRobot.pauseForHuman(1000);
    }

    public void closeWindow() {
        super.closeWindow();

        int eventWaitTimeout = 5000;
        guiRobot.waitForEvent(() -> !guiRobot.isWindowActive(HELP_WINDOW_TITLE), eventWaitTimeout);
    }
}

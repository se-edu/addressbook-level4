package guitests.guihandles;

import static guitests.GuiRobotUtil.LONG_WAIT;
import static guitests.GuiRobotUtil.MEDIUM_WAIT;

/**
 * Provides a handle to the help window of the app.
 */
public class HelpWindowHandle extends GuiHandle {

    private static final String HELP_WINDOW_TITLE = "Help";
    private static final String HELP_WINDOW_ROOT_FIELD_ID = "#helpWindowRoot";

    public HelpWindowHandle() {
        super(HELP_WINDOW_TITLE);
        guiRobot.pauseForHuman(LONG_WAIT);
    }

    public boolean isWindowOpen() {
        return guiRobot.lookup(HELP_WINDOW_ROOT_FIELD_ID).tryQuery().isPresent();
    }

    public void closeWindow() {
        super.closeWindow();
        guiRobot.pauseForHuman(MEDIUM_WAIT);
    }

}

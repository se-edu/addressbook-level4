package guitests.guihandles;

import guitests.GuiRobot;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HelpWindowHandle extends StageHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    public HelpWindowHandle() {
        super(new GuiRobot().getStage(HELP_WINDOW_TITLE));
    }
}

package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle to the help window of the app.
 */
public class HelpWindowHandle extends GuiHandle {

    private static final String HELP_WINDOW_TITLE = "Help";
    private static final String HELP_WINDOW_ROOT_FIELD_ID = "#helpWindowRoot";

    public HelpWindowHandle(Stage primaryStage) {
        super(primaryStage, HELP_WINDOW_TITLE);

        guiRobot.pauseForHuman(1000);
    }

    public boolean isWindowOpen() {
        return guiRobot.lookup(HELP_WINDOW_ROOT_FIELD_ID).tryQuery().isPresent();
    }

    public void closeWindow() {
        super.closeWindow();
        guiRobot.pauseForHuman(500);
    }

}

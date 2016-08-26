package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;
import seedu.address.TestApp;
import seedu.address.controller.StatusBarHeader;

/**
 * A handler for the HeaderStatusBar of the UI
 */
public class HeaderStatusBarHandle extends GuiHandle {

    public HeaderStatusBarHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public String getText() {
        return getStatusBar().getText();
    }

    private StatusBar getStatusBar() {
        return (StatusBar) getNode("#" + StatusBarHeader.HEADER_STATUS_BAR_ID);
    }
}

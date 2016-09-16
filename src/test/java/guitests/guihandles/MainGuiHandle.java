package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;
import seedu.address.TestApp;
import seedu.address.commons.OsDetector;
import seedu.address.model.ModelManager;

import java.util.concurrent.TimeUnit;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public PersonListPanelHandle getPersonListPanel() {
        return new PersonListPanelHandle(guiRobot, primaryStage);
    }

    public ResultDisplayHandle getHeaderStatusBar() {
        return new ResultDisplayHandle(guiRobot, primaryStage);
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(guiRobot, primaryStage);
    }

    public boolean isMinimized() {
        return primaryStage.isIconified() && !primaryStage.isMaximized();
    }

    public boolean isMaximized() {
        return primaryStage.isMaximized() && !primaryStage.isIconified();
    }

    public boolean isDefaultSize() {
        if (OsDetector.isOnMac()) {
            return !primaryStage.isIconified(); // TODO: Find a way to verify this on mac since isMaximized is always true
        } else {
            return !primaryStage.isMaximized() && !primaryStage.isIconified();
        }
    }

    public FxRobot sleepForGracePeriod() {
        return guiRobot.sleep((ModelManager.GRACE_PERIOD_DURATION + 1), TimeUnit.SECONDS);
    }

    public FxRobot sleep(long duration, TimeUnit timeunit) {
        return guiRobot.sleep(duration, timeunit);
    }

}

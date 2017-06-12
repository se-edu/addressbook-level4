package guitests.guihandles;

import static guitests.GuiRobotUtil.MEDIUM_WAIT;

import java.util.Arrays;

import javafx.scene.input.KeyCode;
import seedu.address.TestApp;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends GuiHandle {

    public MainMenuHandle() {
        super(TestApp.APP_TITLE);
    }

    public GuiHandle clickOn(String... menuText) {
        Arrays.stream(menuText).forEach((menuItem) -> guiRobot.clickOn(menuItem));
        return this;
    }

    public void openHelpWindowUsingMenu() {
        clickOn("Help", "F1");
    }

    public void openHelpWindowUsingAccelerator() {
        useF1Accelerator();
    }

    private void useF1Accelerator() {
        guiRobot.push(KeyCode.F1);
        guiRobot.pauseForHuman(MEDIUM_WAIT);
    }
}

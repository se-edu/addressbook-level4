package guitests.guihandles;

import java.util.Arrays;

import javafx.scene.input.KeyCode;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends NodeHandle {

    public MainMenuHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getNode("#menuBar"));
    }

    public void clickOn(String... menuText) {
        Arrays.stream(menuText).forEach((menuItem) -> guiRobot.clickOn(menuItem));
    }

    public void openHelpWindowUsingMenu() {
        clickOn("Help", "F1");
    }

    public void openHelpWindowUsingAccelerator() {
        useF1Accelerator();
    }

    private void useF1Accelerator() {
        guiRobot.push(KeyCode.F1);
        guiRobot.pauseForHuman(500);
    }
}

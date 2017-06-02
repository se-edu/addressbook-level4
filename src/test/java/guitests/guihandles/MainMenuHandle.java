package guitests.guihandles;

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

    public HelpWindowHandle openHelpWindowUsingMenu() {
        clickOn("Help", "F1");
        return new HelpWindowHandle();
    }

    public HelpWindowHandle openHelpWindowUsingAccelerator() {
        useF1Accelerator();
        return new HelpWindowHandle();
    }

    private void useF1Accelerator() {
        guiRobot.push(KeyCode.F1);
        guiRobot.pauseForHuman(500);
    }
}

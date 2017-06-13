package guitests.guihandles;

import java.util.Arrays;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends GuiHandle {
    public MainMenuHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public GuiHandle clickOn(String... menuText) {
        Arrays.stream(menuText).forEach((menuItem) -> guiRobot.clickOn(menuItem));
        return this;
    }

    public HelpWindowHandle openHelpWindowUsingMenu() {
        clickOn("Help", "F1");
        return new HelpWindowHandle();
    }

    public void openHelpWindowUsingAccelerator() {
        useF1Accelerator();
    }

    private void useF1Accelerator() {
        guiRobot.push(KeyCode.F1);
        guiRobot.pauseForHuman();
    }
}

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

    /**
     * Automates the manual click on the given {@code menuText}.
     * @return the MainMenuHandle.
     */
    public GuiHandle clickOn(String... menuText) {
        Arrays.stream(menuText).forEach((menuItem) -> guiRobot.clickOn(menuItem));
        return this;
    }

    /**
     * Automates the manual open help window process on the menu.
     * @return the HelpWindowHandle.
     */
    public HelpWindowHandle openHelpWindowUsingMenu() {
        clickOn("Help", "F1");
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    /**
     * Automates the open help window process using the F1 accelerator.
     * @return the HelpWindowHandle.
     */
    public HelpWindowHandle openHelpWindowUsingAccelerator() {
        useF1Accelerator();
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    private void useF1Accelerator() {
        guiRobot.push(KeyCode.F1);
        guiRobot.sleep(500);
    }
}

package guitests.guihandles;

import java.util.Arrays;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends GuiHandle {

    public MainMenuHandle(Stage primaryStage) {
        super(primaryStage, TestApp.APP_TITLE);
    }

    public GuiHandle clickOn(String... menuText) {
        Arrays.stream(menuText).forEach((menuItem) -> guiRobot.clickOn(menuItem));
        return this;
    }

    public HelpWindowHandle openHelpWindowUsingMenu() {
        clickOn("Help", "F1");
        return new HelpWindowHandle(primaryStage);
    }

    public HelpWindowHandle openHelpWindowUsingAccelerator() {
        useF1Accelerator();
        return new HelpWindowHandle(primaryStage);
    }

    private void useF1Accelerator() {
        guiRobot.push(KeyCode.F1);
        guiRobot.pauseForHuman(500);
    }
}

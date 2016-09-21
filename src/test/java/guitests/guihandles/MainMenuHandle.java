package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import seedu.address.TestApp;

import java.util.Arrays;

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
        clickOn(new String[] {"Help", "F1"});
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    public HelpWindowHandle openHelpWindowUsingAccelerator() {
        use_F1_accelerator();
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    private void use_F1_accelerator() {
        guiRobot.push(KeyCode.F1);
        guiRobot.sleep(500);
    }
}

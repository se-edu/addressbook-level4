package guitests.guihandles;

import java.util.Arrays;

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

}

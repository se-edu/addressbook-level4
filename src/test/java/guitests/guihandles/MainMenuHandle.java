package guitests.guihandles;

import java.util.Arrays;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends NodeHandle {

    private static final String MENU_BAR_ID = "#menuBar";

    public MainMenuHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getChildNode(MENU_BAR_ID));
    }

    public void clickOnMenuItemsSequentially(String... menuItems) {
        Arrays.stream(menuItems).forEach(GUI_ROBOT::clickOn);
    }

    public void openHelpWindowUsingMenu() {
        clickOnMenuItemsSequentially("Help", "F1");
    }

}

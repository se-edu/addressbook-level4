package guitests.guihandles;

import java.util.Arrays;

import javafx.scene.input.KeyCode;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends NodeHandle {
    private static final String MENU_BAR_ID = "#menuBar";

    public MainMenuHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getChildNode(MENU_BAR_ID));
    }

    /**
     * Opens the {@code HelpWindow} using the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingMenu() {
        clickOnMenuItemsSequentially("Help", "F1");
    }

    /**
     * Opens the {@code HelpWindow} by pressing the shortcut key associated
     * to the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingAccelerator() {
        GUI_ROBOT.push(KeyCode.F1);
        GUI_ROBOT.pauseForHuman();
    }

    /**
     * Clicks on the menu items in order.
     */
    public void clickOnMenuItemsSequentially(String... menuItems) {
        Arrays.stream(menuItems).forEach(GUI_ROBOT::clickOn);
    }
}

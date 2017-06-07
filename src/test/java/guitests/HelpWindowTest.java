package guitests;

import static guitests.guihandles.HelpWindowHandle.HELP_WINDOW_TITLE;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow_fromCommandBox_success() {
        mainWindowHandle.getCommandBox().click();
        mainWindowHandle.getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.focusOnWindow();   // needed in headless mode, otherwise main window loses focus
        mainWindowHandle.getResultDisplay().click();
        mainWindowHandle.getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.focusOnWindow();
        mainWindowHandle.getPersonListPanel().click();
        mainWindowHandle.getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.focusOnWindow();
        mainWindowHandle.getBrowserPanel().click();
        mainWindowHandle.getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowNotOpen();

        mainWindowHandle.focusOnWindow();
        mainWindowHandle.getMainMenu().openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        mainWindowHandle.focusOnWindow();
        mainWindowHandle.getCommandBox().runHelpCommand();
        assertHelpWindowOpen();
    }

    /**
     * Asserts that the help window is open, and closes it after we are done checking.
     */
    private void assertHelpWindowOpen() {
        GuiRobot guiRobot = new GuiRobot();

        int eventWaitTimeout = 5000;
        guiRobot.waitForEvent(() -> guiRobot.isWindowActive(HELP_WINDOW_TITLE), eventWaitTimeout);
        new HelpWindowHandle().closeWindow();
    }

    /**
     * Asserts that the help window isn't open at all.
     */
    private void assertHelpWindowNotOpen() {
        GuiRobot guiRobot = new GuiRobot();
        assertFalse(guiRobot.isWindowActive(HELP_WINDOW_TITLE));
    }

}

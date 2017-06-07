package guitests;

import static guitests.guihandles.HelpWindowHandle.HELP_WINDOW_TITLE;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow() {
        //use accelerator
        mainWindowHandle.getCommandBox().click();
        mainWindowHandle.getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.getResultDisplay().click();
        mainWindowHandle.getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.getPersonListPanel().click();
        mainWindowHandle.getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.getBrowserPanel().click();
        mainWindowHandle.getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowNotOpen();

        //use menu button
        mainWindowHandle.getMainMenu().openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        //use command
        mainWindowHandle.getCommandBox().runHelpCommand();
        assertHelpWindowOpen();
    }

    private void assertHelpWindowOpen() {
        GuiRobot guiRobot = new GuiRobot();

        int eventWaitTimeout = 5000;
        guiRobot.waitForEvent(() -> guiRobot.isWindowActive(HELP_WINDOW_TITLE), eventWaitTimeout);
        new HelpWindowHandle().closeWindow();
    }

    private void assertHelpWindowNotOpen() {
        GuiRobot guiRobot = new GuiRobot();
        assertFalse(guiRobot.isWindowActive(HELP_WINDOW_TITLE));
    }

}

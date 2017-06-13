package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends AddressBookGuiTest {

    private GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openHelpWindow() {
        // use accelerator
        getCommandBox().clickOnSelf();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.focusOnWindow();   // needed in headless mode, otherwise main window loses focus
        getResultDisplay().clickOnSelf();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.focusOnWindow();
        getPersonListPanel().clickOnSelf();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.focusOnWindow();
        getBrowserPanel().clickOnSelf();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowNotOpen();

        // use menu button
        mainWindowHandle.focusOnWindow();
        getMainMenu().openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        // use command box
        mainWindowHandle.focusOnWindow();
        getCommandBox().runHelpCommand();
        assertHelpWindowOpen();
    }

    /**
     * Asserts that the help window is open, and closes it after we are done checking.
     */
    private void assertHelpWindowOpen() {
        assertTrue(HelpWindowHandle.isWindowPresent());
        guiRobot.sleep(500);

        new HelpWindowHandle().closeWindow();
    }

    /**
     * Asserts that the help window isn't open at all.
     */
    private void assertHelpWindowNotOpen() {
        guiRobot.sleep(500);

        assertFalse(HelpWindowHandle.isWindowPresent());
    }

}

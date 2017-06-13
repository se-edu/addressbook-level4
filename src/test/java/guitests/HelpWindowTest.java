package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends AddressBookGuiTest {

    private GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openHelpWindow() {
        //use accelerator
        getCommandBox().clickOnRootNode();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.focusOnStage();   // needed in headless mode, otherwise main window loses focus
        getResultDisplay().clickOnTextArea();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.focusOnStage();
        getPersonListPanel().clickOnListView();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        mainWindowHandle.focusOnStage();
        getBrowserPanel().clickOnRootNode();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowNotOpen();

        //use menu button
        mainWindowHandle.focusOnStage();
        getMainMenu().openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        //use command box
        mainWindowHandle.focusOnStage();
        getCommandBox().runHelpCommand();
        assertHelpWindowOpen();
    }

    /**
     * Asserts that the help window is open, and closes it after we are done checking.
     */
    private void assertHelpWindowOpen() {
        assertTrue(HelpWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new HelpWindowHandle().closeWindow();
    }

    /**
     * Asserts that the help window isn't open.
     */
    private void assertHelpWindowNotOpen() {
        guiRobot.pauseForHuman();

        assertFalse(HelpWindowHandle.isWindowPresent());
    }

}

package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow() {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        getResultDisplay().clickOnTextArea();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        getPersonListPanel().clickOnListView();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        getBrowserPanel().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowNotOpen();

        //use menu button
        getMainMenu().openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        //use command box
        getCommandBox().runHelpCommand();
        assertHelpWindowOpen();
    }

    /**
     * Asserts that the help window is open, and closes it after we are done checking.
     */
    private void assertHelpWindowOpen() {
        assertTrue(guiRobot.isWindowShown(HelpWindowHandle.HELP_WINDOW_TITLE));
        guiRobot.pauseForHuman();

        guiRobot.interact(() -> guiRobot.getStage(HelpWindowHandle.HELP_WINDOW_TITLE).close());
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the help window isn't open.
     */
    private void assertHelpWindowNotOpen() {
        assertFalse(guiRobot.isWindowShown(HelpWindowHandle.HELP_WINDOW_TITLE));
    }

}

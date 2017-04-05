package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;
import seedu.address.logic.commands.HelpCommand;

/**
 *
 */
public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow() {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        getResultDisplay().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        getPersonListPanel().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        getBrowserPanel().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowNotOpen();

        //use menu button
        getMainMenu().openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        //use command box
        runCommand(HelpCommand.COMMAND_WORD);
        assertHelpWindowOpen();
    }

    /**
     * Asserts that the help window is open, and closes it after checking.
     */
    private void assertHelpWindowOpen() {
        assertTrue(HelpWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new HelpWindowHandle(guiRobot.getStage(HelpWindowHandle.HELP_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the help window isn't open.
     */
    private void assertHelpWindowNotOpen() {
        assertFalse(HelpWindowHandle.isWindowPresent());
    }

}

package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;
import seedu.address.logic.commands.HelpCommand;

public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow() {
        //use accelerator
        getCommandBox().clickOnTextField();
        assertHelpWindowOpen(getMainMenu().openHelpWindowUsingAccelerator());

        getResultDisplay().clickOnTextArea();
        assertHelpWindowOpen(getMainMenu().openHelpWindowUsingAccelerator());

        getPersonListPanel().clickOnListView();
        assertHelpWindowOpen(getMainMenu().openHelpWindowUsingAccelerator());

        getBrowserPanel().clickOnWebView();
        assertHelpWindowNotOpen(getMainMenu().openHelpWindowUsingAccelerator());

        //use menu button
        assertHelpWindowOpen(getMainMenu().openHelpWindowUsingMenu());

        //use command
        runCommand(HelpCommand.COMMAND_WORD);
        assertHelpWindowOpen(new HelpWindowHandle());
    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }

    private void assertHelpWindowNotOpen(HelpWindowHandle helpWindowHandle) {
        assertFalse(helpWindowHandle.isWindowOpen());
    }

}

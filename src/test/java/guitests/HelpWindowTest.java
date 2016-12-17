package guitests;

import guitests.guihandles.HelpWindowHandle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow() {
        //use accelerator
        commandBox.clickOnTextField();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        resultDisplay.clickOnTextArea();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        personListPanel.clickOnListView();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        browserPanel.clickOnWebView();
        assertHelpWindowNotOpen(mainMenu.openHelpWindowUsingAccelerator());

        //use menu button
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        //use command
        assertHelpWindowOpen(commandBox.runHelpCommand());
    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }

    private void assertHelpWindowNotOpen(HelpWindowHandle helpWindowHandle) {
        assertFalse(helpWindowHandle.isWindowOpen());
    }

}

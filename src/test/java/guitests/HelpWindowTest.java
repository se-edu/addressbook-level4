package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow() {
        //use accelerator
        commandBox.clickOnTextField();
        mainMenu.openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        resultDisplay.clickOnTextArea();
        mainMenu.openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        personListPanel.clickOnListView();
        mainMenu.openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        browserPanel.clickOnWebView();
        mainMenu.openHelpWindowUsingAccelerator();
        assertHelpWindowNotOpen();

        //use menu button
        mainMenu.openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        //use command
        commandBox.runHelpCommand();
        assertHelpWindowOpen();
    }

    private void assertHelpWindowOpen() {
        HelpWindowHandle helpWindowHandle = new HelpWindowHandle();
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }

    private void assertHelpWindowNotOpen() {
        HelpWindowHandle helpWindowHandle = new HelpWindowHandle();
        assertFalse(helpWindowHandle.isWindowOpen());
    }

}

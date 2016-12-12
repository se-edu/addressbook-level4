package guitests;

import guitests.guihandles.HelpWindowHandle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow() {

        commandBox.clickOnTextField();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        resultDisplay.clickOnTextArea();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        personListPanel.clickOnListView();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        assertHelpWindowOpen(commandBox.runHelpCommand());

    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}

package guitests;

import guitests.guihandles.HelpWindowHandle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow() {

        assertHelpWindowOpen(commandBox.runHelpCommand());

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());
    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}

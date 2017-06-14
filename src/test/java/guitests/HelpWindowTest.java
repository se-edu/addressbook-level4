package guitests;

import static guitests.GuiRobotUtil.EVENT_TIMEOUT;
import static guitests.guihandles.HelpWindowHandle.HELP_WINDOW_TITLE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;
import javafx.scene.input.KeyCode;
import seedu.address.logic.commands.HelpCommand;

public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow() {
        //use accelerator
        getCommandBox().clickOnTextField();
        guiRobot.push(KeyCode.F1);
        assertHelpWindowOpen();

        getResultDisplay().clickOnTextArea();
        guiRobot.push(KeyCode.F1);
        assertHelpWindowOpen();

        getPersonListPanel().clickOnListView();
        guiRobot.push(KeyCode.F1);
        assertHelpWindowOpen();

        getBrowserPanel().clickOnWebView();
        guiRobot.push(KeyCode.F1);
        assertHelpWindowNotOpen();

        //use menu button
        getMainMenu().openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        //use command
        getCommandBox().submitCommand(HelpCommand.COMMAND_WORD);
        assertHelpWindowOpen();
    }

    private void assertHelpWindowOpen() {
        guiRobot.waitForEvent(() -> guiRobot.isWindowActive(HELP_WINDOW_TITLE), EVENT_TIMEOUT);

        HelpWindowHandle helpWindowHandle = new HelpWindowHandle();
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }

    private void assertHelpWindowNotOpen() {
        HelpWindowHandle helpWindowHandle = new HelpWindowHandle();
        assertFalse(helpWindowHandle.isWindowOpen());
    }

}

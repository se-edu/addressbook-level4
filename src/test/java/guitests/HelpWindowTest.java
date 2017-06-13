package guitests;

import static guitests.GuiRobotUtil.MEDIUM_WAIT;
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
        guiRobot.pauseForHuman(MEDIUM_WAIT);
        assertHelpWindowOpen();

        getResultDisplay().clickOnTextArea();
        guiRobot.push(KeyCode.F1);
        guiRobot.pauseForHuman(MEDIUM_WAIT);
        assertHelpWindowOpen();

        getPersonListPanel().clickOnListView();
        guiRobot.push(KeyCode.F1);
        guiRobot.pauseForHuman(MEDIUM_WAIT);
        assertHelpWindowOpen();

        getBrowserPanel().clickOnWebView();
        guiRobot.push(KeyCode.F1);
        guiRobot.pauseForHuman(MEDIUM_WAIT);
        assertHelpWindowNotOpen();

        //use menu button
        getMainMenu().openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        //use command
        getCommandBox().enterCommand(HelpCommand.COMMAND_WORD);
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

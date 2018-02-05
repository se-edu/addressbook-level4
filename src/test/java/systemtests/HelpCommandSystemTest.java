package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.HelpWindowHandle;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.ui.BrowserPanel;

/**
 * TODO: This test is incomplete as it is missing test cases.
 */
public class HelpCommandSystemTest extends AddressBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

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
        executeCommand(HelpCommand.COMMAND_WORD);
        assertHelpWindowOpen();

        //use command box to open help window and then assert that the UI has been updated
        executeCommand(HelpCommand.COMMAND_WORD);
        getMainWindowHandle().focus();
        assertUiUpdated();
    }

    /**
     * Asserts that the help window is open, and closes it after checking.
     */
    private void assertHelpWindowOpen() {
        assertTrue(ERROR_MESSAGE, HelpWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new HelpWindowHandle(guiRobot.getStage(HelpWindowHandle.HELP_WINDOW_TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the help window isn't open.
     */
    private void assertHelpWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, HelpWindowHandle.isWindowPresent());
    }

    /**
     * After execution of multiple commands, asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box is not empty.<br>
     * 4. BrowserPanel does not show the default page.<br>
     * 5. Status bar sync status changes.<br>
     * 6.{@code PersonListPanel} equals to the corresponding components in
     * the current model.<br>
     */
    private void assertUiUpdated() {
        executeCommand(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        executeCommand(DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        assertFalse(getResultDisplay().getText().isEmpty());
        assertFalse(getBrowserPanel().getLoadedUrl().equals(BrowserPanel.DEFAULT_PAGE));
        assertStatusBarUnchangedExceptSyncStatus();
        assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());
    }

}

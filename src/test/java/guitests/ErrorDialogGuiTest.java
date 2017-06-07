package guitests;

import static guitests.GuiRobotUtil.LONG_WAIT;
import static junit.framework.TestCase.assertTrue;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.AlertDialogHandle;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;

public class ErrorDialogGuiTest extends AddressBookGuiTest {

    private static final String ERROR_DIALOG_STAGE_TITLE = "File Op Error";
    private static final String ERROR_HEADER_MESSAGE = "Could not save data";
    private static final String ERROR_CONTENT_MESSAGE = "Could not save data to file";

    @Test
    public void showErrorDialogs() {
        GuiRobot guiRobot = GuiRobot.getInstance();

        // create a stub exception
        raise(new DataSavingExceptionEvent(new IOException()));

        int eventWaitTimeout = 5000;
        guiRobot.waitForEvent(() -> guiRobot.isWindowActive(ERROR_DIALOG_STAGE_TITLE), eventWaitTimeout);
        guiRobot.pauseForHuman(LONG_WAIT);

        AlertDialogHandle alertDialog = new AlertDialogHandle(guiRobot.window(ERROR_DIALOG_STAGE_TITLE));
        assertTrue(alertDialog.isMatchingContent(ERROR_HEADER_MESSAGE, ERROR_CONTENT_MESSAGE + ":\n"
                                                                         + "java.io.IOException"));

    }
}

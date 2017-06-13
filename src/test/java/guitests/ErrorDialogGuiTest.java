package guitests;

import static guitests.GuiRobotUtil.EVENT_TIMEOUT;
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
    public void showErrorDialogs() throws InterruptedException {
        GuiRobot guiRobot = GuiRobot.getInstance();

        raise(new DataSavingExceptionEvent(new IOException()));

        guiRobot.waitForEvent(() -> guiRobot.isWindowActive(ERROR_DIALOG_STAGE_TITLE), EVENT_TIMEOUT);
        guiRobot.pauseForHuman(LONG_WAIT);

        AlertDialogHandle alertDialog = new AlertDialogHandle(ERROR_DIALOG_STAGE_TITLE);
        assertTrue(alertDialog.isMatching(ERROR_HEADER_MESSAGE, ERROR_CONTENT_MESSAGE + ":\n"
                                                                         + "java.io.IOException"));

    }

}

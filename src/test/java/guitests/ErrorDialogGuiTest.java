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

    @Test
    public void showErrorDialogs() throws InterruptedException {
        GuiRobot guiRobot = new GuiRobot();

        raise(new DataSavingExceptionEvent(new IOException("Stub")));

        guiRobot.waitForEvent(() -> guiRobot.isWindowActive(ERROR_DIALOG_STAGE_TITLE), EVENT_TIMEOUT);
        guiRobot.pauseForHuman(LONG_WAIT);

        AlertDialogHandle alertDialog = mainGui.getAlertDialog(ERROR_DIALOG_STAGE_TITLE);
        assertTrue(alertDialog.isMatching("Could not save data", "Could not save data to file" + ":\n"
                                                                         + "java.io.IOException: Stub"));

    }

}

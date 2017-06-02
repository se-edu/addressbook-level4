package guitests;

import static junit.framework.TestCase.assertTrue;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.AlertDialogHandle;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;

public class ErrorDialogGuiTest extends AddressBookGuiTest {

    private final String errorDialogStageTitle = "File Op Error";

    @Test
    public void showErrorDialogs() throws InterruptedException {
        GuiRobot guiRobot = new GuiRobot();

        raise(new DataSavingExceptionEvent(new IOException("Stub")));

        int eventWaitTimeout = 5000;
        guiRobot.waitForEvent(() -> guiRobot.isWindowActive(errorDialogStageTitle), eventWaitTimeout);

        AlertDialogHandle alertDialog = mainGui.getAlertDialog(errorDialogStageTitle);
        assertTrue(alertDialog.isMatching("Could not save data", "Could not save data to file" + ":\n"
                                                                         + "java.io.IOException: Stub"));

    }

}

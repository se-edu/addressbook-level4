package guitests;

import static junit.framework.TestCase.assertTrue;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.AlertDialogHandle;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;

public class ErrorDialogGuiTest extends AddressBookGuiTest {

    private static final String ERROR_DIALOG_STAGE_TITLE = "File Op Error";

    @Test
    public void showErrorDialogs() throws Exception {
        GuiRobot guiRobot = new GuiRobot();

        raise(new DataSavingExceptionEvent(new IOException("Stub")));

        guiRobot.waitForEvent(() -> guiRobot.isWindowShown(ERROR_DIALOG_STAGE_TITLE));
        AlertDialogHandle alertDialog = new AlertDialogHandle(guiRobot.getStage(ERROR_DIALOG_STAGE_TITLE).get());
        assertTrue(alertDialog.isMatching("Could not save data", "Could not save data to file" + ":\n"
                                                                         + "java.io.IOException: Stub"));

    }

}

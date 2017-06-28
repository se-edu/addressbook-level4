package guitests;

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
        GuiRobot guiRobot = new GuiRobot();

        // create a stub exception
        raise(new DataSavingExceptionEvent(new IOException("Stub")));

        guiRobot.waitForEvent(() -> guiRobot.isWindowShown(ERROR_DIALOG_STAGE_TITLE));
        AlertDialogHandle alertDialog = new AlertDialogHandle(ERROR_DIALOG_STAGE_TITLE);
        assertTrue(alertDialog.isMatching(ERROR_HEADER_MESSAGE, ERROR_CONTENT_MESSAGE + ":\n"
                                                                         + "java.io.IOException: Stub"));

    }

}

package guitests;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.UiManager.FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE;
import static seedu.address.ui.UiManager.FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE;
import static seedu.address.ui.UiManager.FILE_OPS_ERROR_DIALOG_STAGE_TITLE;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.AlertDialogHandle;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;

public class ErrorDialogGuiTest extends AddressBookGuiTest {

    private static final IOException IO_EXCEPTION_STUB = new IOException("Stub");

    @Test
    public void showErrorDialogs() throws Exception {
        raise(new DataSavingExceptionEvent(new IOException("Stub")));

        guiRobot.waitForEvent(() -> guiRobot.isWindowShown(ERROR_DIALOG_STAGE_TITLE));
        AlertDialogHandle alertDialog = new AlertDialogHandle(guiRobot.getStage(ERROR_DIALOG_STAGE_TITLE));
        assertTrue(alertDialog.isMatching("Could not save data", "Could not save data to file" + ":\n"
                                                                         + "java.io.IOException: Stub"));

        AlertDialogHandle alertDialog = new AlertDialogHandle(FILE_OPS_ERROR_DIALOG_STAGE_TITLE);
        assertEquals(FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE, alertDialog.getHeaderText());
        assertEquals(FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE + ":\n" + IO_EXCEPTION_STUB.toString(),
                alertDialog.getContentText());
    }

}

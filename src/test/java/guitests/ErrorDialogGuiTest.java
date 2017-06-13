package guitests;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.ui.UiManager.FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE;
import static seedu.address.ui.UiManager.FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE;
import static seedu.address.ui.UiManager.FILE_OPS_ERROR_DIALOG_STAGE_TITLE;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.AlertDialogHandle;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;

public class ErrorDialogGuiTest extends AddressBookGuiTest {

    @Test
    public void showErrorDialogs() {
        GuiRobot guiRobot = new GuiRobot();

        raise(new DataSavingExceptionEvent(new IOException("Stub")));

        guiRobot.waitForEvent(() -> guiRobot.isWindowShown(FILE_OPS_ERROR_DIALOG_STAGE_TITLE));
        AlertDialogHandle alertDialog = new AlertDialogHandle(FILE_OPS_ERROR_DIALOG_STAGE_TITLE);
        assertTrue(alertDialog.isMatching(FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE,
                FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE + ":\n" + "java.io.IOException: Stub"));

    }

}

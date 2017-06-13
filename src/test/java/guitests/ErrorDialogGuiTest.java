package guitests;

import static junit.framework.TestCase.assertTrue;

import java.io.IOException;

import org.junit.Test;

import guitests.guihandles.AlertDialogHandle;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;

public class ErrorDialogGuiTest extends AddressBookGuiTest {

    @Test
    public void showErrorDialogs() throws InterruptedException {
        //Test DataSavingExceptionEvent dialog
        raise(new DataSavingExceptionEvent(new IOException("Stub")));

        GuiRobot guiRobot = new GuiRobot();
        guiRobot.sleep(500); // wait for the alert dialog box to launch
        AlertDialogHandle alertDialog = new AlertDialogHandle(new GuiRobot(), stage, "File Op Error");
        assertTrue(alertDialog.isMatching("Could not save data", "Could not save data to file" + ":\n"
                                                                         + "java.io.IOException: Stub"));

    }

}

package guitests;

import guitests.guihandles.AlertDialogHandle;
import org.junit.Test;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class ErrorDialogGuiTest extends AddressBookGuiTest{

    @Test
    public void showErrorDialogs() throws InterruptedException {
        //Test DataSavingExceptionEvent dialog
        raise(new DataSavingExceptionEvent(new IOException("Stub")));
        AlertDialogHandle alertDialog = mainGui.getAlertDialog("File Op Error");
        assertTrue(alertDialog.isMatching("Could not save data", "Could not save data to file" + ":\n"
                                                                         + "java.io.IOException: Stub"));

    }

}

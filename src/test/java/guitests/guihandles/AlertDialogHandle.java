package guitests.guihandles;

import javafx.scene.control.DialogPane;
import seedu.address.ui.UiManager;

/**
 * A handle for the AlertDialog of the UI
 */
public class AlertDialogHandle extends GuiHandle {

    public AlertDialogHandle(String dialogTitle) {
        super(dialogTitle);
    }

    private DialogPane getDialogPane() {
        return getNode("#" + UiManager.ALERT_DIALOG_PANE_FIELD_ID);
    }

    public String getHeaderText() {
        return getDialogPane().getHeaderText();
    }

    public String getContentText() {
        return getDialogPane().getContentText();
    }
}

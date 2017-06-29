package guitests.guihandles;

import static seedu.address.commons.util.AppUtil.checkArgument;

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

    public boolean isMatching(String headerMessage, String contentMessage) {
        checkArgument(intermediateStage.isPresent(), "Alert dialog is not present");
        boolean isMatching = getHeaderText().equals(headerMessage) && getContentText().equals(contentMessage);
        return isMatching;
    }
}

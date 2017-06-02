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

    public boolean isMatching(String headerMessage, String contentMessage) {
        checkArgument(intermediateStage.isPresent(), "Alert dialog is not present");
        DialogPane dialogPane = getNode("#" + UiManager.ALERT_DIALOG_PANE_FIELD_ID);
        boolean isMatching = dialogPane.getHeaderText().equals(headerMessage)
                && dialogPane.getContentText().equals(contentMessage);
        return isMatching;
    }
}

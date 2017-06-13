package guitests.guihandles;

import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import seedu.address.ui.UiManager;

/**
 * A handle for the {@code AlertDialog} of the UI.
 */
public class AlertDialogHandle extends StageHandle {

    public AlertDialogHandle(Stage stage) {
        super(stage);
    }

    /**
     * Checks that the content of the {@code AlertDialog} matches that of {@code headerMessage} and
     * {@code contentMessage}.
     */
    public boolean isMatching(String headerMessage, String contentMessage) {
        DialogPane dialogPane = getChildNode("#" + UiManager.ALERT_DIALOG_PANE_FIELD_ID);
        boolean isMatching = dialogPane.getHeaderText().equals(headerMessage)
                && dialogPane.getContentText().equals(contentMessage);
        return isMatching;
    }
}

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

    private DialogPane getDialogPane() {
        return getChildNode("#" + UiManager.ALERT_DIALOG_PANE_FIELD_ID);
    }

    /**
     * Returns the text of the header in the {@code AlertDialog}.
     */
    public String getHeaderText() {
        return getDialogPane().getHeaderText();
    }

    /**
     * Returns the text of the content in the {@code AlertDialog}.
     */
    public String getContentText() {
        return getDialogPane().getContentText();
    }
}

package guitests.guihandles;

import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import seedu.address.ui.UiManager;

/**
 * A handle for the {@code AlertDialog} of the UI.
 */
public class AlertDialogHandle extends StageHandle {
    private final DialogPane dialogPane;

    public AlertDialogHandle(Stage stage) {
        super(stage);

        this.dialogPane = getChildNode("#" + UiManager.ALERT_DIALOG_PANE_FIELD_ID);
    }

    /**
     *
     */
    public boolean isMatching(String headerMessage, String contentMessage) {
        assert intermediateStage.isPresent() : "Alert dialog is not present";
        DialogPane dialogPane = getNode("#" + UiManager.ALERT_DIALOG_PANE_FIELD_ID);
        boolean isMatching = dialogPane.getHeaderText().equals(headerMessage)
                && dialogPane.getContentText().equals(contentMessage);
        return isMatching;
    }
}

package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import seedu.address.ui.UiManager;

/**
 * A handler for the AlertDialog of the UI
 */
public class AlertDialogHandle extends GuiHandle {


    public AlertDialogHandle(GuiRobot guiRobot, Stage primaryStage, String dialogTitle) {
        super(guiRobot, primaryStage, dialogTitle);
    }

    public boolean isAlertDialogValid(String headingMessage, String contentMessage) {
        assert intermediateStage.isPresent() : "Alert dialog is not present";
        DialogPane dialogPane = (DialogPane) intermediateStage.get().getScene().lookup("#" + UiManager.ALERT_DIALOG_PANE_FIELD_ID);
        boolean isValid = dialogPane.getHeaderText().equals(headingMessage) && dialogPane.getContentText().equals(contentMessage);
        return isValid;
    }
}

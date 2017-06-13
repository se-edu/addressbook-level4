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

    /**
     * Checks that the content of the {@code AlertDialog} matches that of {@code expectedHeaderMessage} and
     * {@code expectedContentMessage}.
     */
    public boolean isMatchingContent(String expectedHeaderMessage, String expectedContentMessage) {
        checkArgument(intermediateStage.isPresent(), "Alert dialog is not present");
        DialogPane dialogPane = getNode("#" + UiManager.ALERT_DIALOG_PANE_FIELD_ID);

        return (dialogPane.getHeaderText().equals(expectedHeaderMessage)
                && dialogPane.getContentText().equals(expectedContentMessage));
    }
}

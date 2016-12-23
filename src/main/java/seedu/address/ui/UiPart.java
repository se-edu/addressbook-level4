package seedu.address.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;

/**
 * Base class for UI parts.
 * A 'UI part' represents a distinct part of the UI. e.g. Windows, dialogs, panels, status bars, etc.
 */
public abstract class UiPart {

    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    protected void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Sets the main node of the UiPart.
     * Override this method to receive the main Node generated while loading the view from the .fxml file.
     * @param node
     */
    public abstract void setNode(Node node);

    /**
     * Returns the path to the fxml file.
     * Override this method to return the name of the fxml file. e.g. {@code "MainWindow.fxml"}
     */
    public abstract String getFxmlPath();

    /**
     * Creates a modal dialog.
     * @param title Title of the dialog.
     * @param parentStage The owner stage of the dialog.
     * @param scene The scene that will contain the dialog.
     * @return the created dialog, not yet made visible.
     */
    protected Stage createDialogStage(String title, Stage parentStage, Scene scene) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    /**
     * Sets the placeholder for UI parts that reside inside another UI part.
     * @param placeholder
     */
    public void setPlaceholder(AnchorPane placeholder) {
        //Do nothing by default.
    }

}

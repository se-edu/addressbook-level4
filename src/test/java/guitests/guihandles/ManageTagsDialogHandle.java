package guitests.guihandles;

import address.controller.MainController;
import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides a handle to the dialog used for managing tags.
 */
public class ManageTagsDialogHandle extends GuiHandle {

    public static final String EDIT_TAG_TEXT_FIELD = "#tagNameField";
    public static final String TAG_LIST_SCROLL_PANE_FIELD_ID = "#tagListScrollPane";
    public static final String TAG_NAME_FIELD_ID = "#tagName";

    public ManageTagsDialogHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, MainController.DIALOG_TITLE_TAG_LIST);
    }

    private ScrollPane getScrollPane() {
        return (ScrollPane) getNode(TAG_LIST_SCROLL_PANE_FIELD_ID);
    }

    public List<String> getTagNames() {
        ObservableList<Node> childrenUnmodifiable = ((VBox) getScrollPane().getContent()).getChildren();
        return childrenUnmodifiable.stream().map(n -> ((Label) n.lookup(TAG_NAME_FIELD_ID)).getText())
                                            .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean contains(String value) {
        return getTagNames().stream().filter(value::equals).findAny().isPresent();
    }

}

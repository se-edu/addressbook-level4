package seedu.address.controller;

import seedu.address.model.ModelManager;
import seedu.address.model.datatypes.tag.Tag;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Dialog to show the list of available tags
 *
 * Stage, tags, mainController and modelManager should be set before showing stage
 */
public class TagListController extends UiController {
    Stage stage;
    MainController mainController;
    ModelManager modelManager;

    @FXML
    private AnchorPane tagListMainPane;
    @FXML
    private ScrollPane tagListScrollPane;

    public void setStage(Stage stage) {
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                e.consume();
                stage.close();
            }
        });
        this.stage = stage;
    }

    public void setTags(ObservableList<Tag> tagList) {
        tagListScrollPane.setContent(getTagsVBox(tagList, mainController));
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setModelManager(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public VBox getTagsVBox(ObservableList<Tag> tagList, MainController mainController) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER_LEFT);

        if (tagList.size() == 0) {
            vBox.getChildren().add(new Label("No Tags to show"));
            return vBox;
        }
        tagList.forEach(tag -> {
            TagCardController tagCardController = new TagCardController(tag, mainController, this);
            vBox.getChildren().add(tagCardController.getLayout());
        });
        return vBox;
    }
}

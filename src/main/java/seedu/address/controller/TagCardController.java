package seedu.address.controller;

import seedu.address.model.datatypes.tag.Tag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * A controller for TagList's card
 */
public class TagCardController {

    private static final String VIEW_TAG_LIST_CARD_FXML = "/view/TagListCard.fxml";

    @FXML
    private VBox box;
    @FXML
    private Label tagName;

    private Tag tag;
    private Ui ui;
    private TagListController tagListController;

    public TagCardController(Tag tag, Ui ui, TagListController tagListController) {
        this.ui = ui;
        this.tag = tag;
        this.tagListController = tagListController;

        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_TAG_LIST_CARD_FXML));
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        tagName.setText(tag.getName());
    }

    public VBox getLayout() {
        return box;
    }

}

package seedu.address.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.events.EventManager;
import seedu.address.events.controller.PersonPanelSelectionChangedEvent;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.util.LoggerManager;

import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart {
    private final Logger logger = LoggerManager.getLogger(PersonListPanel.class);
    private static final String FXML = "PersonListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyPerson> personListView;

    public PersonListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static PersonListPanel load(Stage primaryStage, AnchorPane personListPlaceholder,
                                       ModelManager modelManager) {
        PersonListPanel personListPanel =
                UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new PersonListPanel());
        personListPanel.configure(modelManager.getFilteredPersonList());
        return personListPanel;
    }

    private void configure(ObservableList<ReadOnlyPerson> personList) {
        setConnections(personList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                EventManager.getInstance().post(new PersonPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public ObservableList<ReadOnlyPerson> getDisplayedPersonsView() {
        return this.personListView.getItems();
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class PersonListViewCell extends ListCell<ReadOnlyPerson> {

        public PersonListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyPerson person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(PersonCard.load(person, getIndex() + 1).getLayout());
            }
        }
    }

}

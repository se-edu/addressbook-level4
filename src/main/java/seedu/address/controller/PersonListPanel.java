package seedu.address.controller;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.browser.BrowserManager;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.PersonListViewCell;
import seedu.address.util.LoggerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Dialog to view the list of persons and their details
 *
 * setConnections should be set before showing stage
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
                                       ModelManager modelManager, BrowserManager browserManager) {
        PersonListPanel personListPanel =
                UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new PersonListPanel());
        personListPanel.configure(browserManager, modelManager.getFilteredPersonList());
        return personListPanel;
    }

    private void configure(BrowserManager browserManager, ObservableList<ReadOnlyPerson> personList) {
        setConnections(browserManager, personList);
        addToPlaceholder();
    }

    public void setConnections(BrowserManager browserManager, ObservableList<ReadOnlyPerson> personList) {
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        loadGithubProfilePageWhenPersonIsSelected(browserManager);
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void loadGithubProfilePageWhenPersonIsSelected(BrowserManager browserManager) {
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Person in list view clicked. Loading GitHub profile page: '" + newValue + "'");
                browserManager.loadPersonPage(newValue);
            }
        });
    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    public List<ReadOnlyPerson> getSelectedPersons() {
        return new ArrayList<>(personListView.getSelectionModel().getSelectedItems());
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

}

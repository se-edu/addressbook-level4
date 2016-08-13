package seedu.address.controller;

import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.events.controller.JumpToListRequestEvent;
import seedu.address.model.ModelManager;
import seedu.address.model.datatypes.person.ReadOnlyPerson;
import seedu.address.ui.PersonListViewCell;
import seedu.address.util.AppLogger;
import seedu.address.util.LoggerManager;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.util.List;
import java.util.Objects;

/**
 * Dialog to view the list of persons and their details
 *
 * setConnections should be set before showing stage
 */
public class PersonListPanel extends BaseUiPart {
    private static AppLogger logger = LoggerManager.getLogger(PersonListPanel.class);
    public static final String FXML = "PersonListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyPerson> personListView;

    private Ui ui;
    private ModelManager modelManager;

    public PersonListPanel() {
        super();
    }

    public static PersonListPanel load(Stage primaryStage, AnchorPane personListPlaceholder,
                                       Ui ui, ModelManager modelManager) {
        logger.debug("Loading person list panel.");
        PersonListPanel personListPanel =
                UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new PersonListPanel());
        personListPanel.configure(ui, modelManager, modelManager.getPersonsAsReadOnlyObservableList());
        return personListPanel;
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void configure(Ui ui, ModelManager modelManager, ObservableList<ReadOnlyPerson> personList){
        setConnections(ui, modelManager, personList);
        addToPlaceholder();
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

    public void setConnections(Ui ui, ModelManager modelManager,
                               ObservableList<ReadOnlyPerson> personList) {
        this.ui = ui;
        this.modelManager = modelManager;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        loadGithubProfilePageWhenPersonIsSelected(ui);
        setupListviewSelectionModelSettings();
    }



    private void setupListviewSelectionModelSettings() {
        personListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        personListView.getItems().addListener((ListChangeListener<ReadOnlyPerson>) c -> {
            while(c.next()) {
                if (c.wasRemoved()) {
                    ObservableList<Integer> currentIndices = personListView.getSelectionModel().getSelectedIndices();
                    if (currentIndices.size() > 1) {
                        personListView.getSelectionModel().clearAndSelect(currentIndices.get(0));
                    }
                }
            }
        });
    }

    private void loadGithubProfilePageWhenPersonIsSelected(Ui ui) {
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.debug("Person in list view clicked. Loading GitHub profile page: '{}'", newValue);
                ui.loadGithubProfilePage(newValue);
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

    private String collateNames(List<ReadOnlyPerson> list) {
        StringBuilder sb = new StringBuilder();
        list.stream().forEach(p -> sb.append(p.fullName() + ", "));
        return sb.toString().substring(0, sb.toString().length() - 2);
    }
}

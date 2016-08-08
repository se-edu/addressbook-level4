package address.controller;

import address.events.controller.JumpToListRequestEvent;
import address.events.parser.FilterCommittedEvent;
import address.model.ModelManager;
import address.model.datatypes.person.ReadOnlyPerson;
import address.parser.expr.PredExpr;
import address.parser.qualifier.TrueQualifier;
import address.ui.PersonListViewCell;
import address.util.AppLogger;
import address.util.LoggerManager;
import address.util.collections.FilteredList;
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
public class PersonListPanelController extends UiController {
    private static AppLogger logger = LoggerManager.getLogger(PersonListPanelController.class);

    @FXML
    private ListView<ReadOnlyPerson> personListView;

    private MainController mainController;
    private ModelManager modelManager;
    private FilteredList<ReadOnlyPerson> filteredPersonList;


    public PersonListPanelController() {
        super();
    }

    public void setConnections(MainController mainController, ModelManager modelManager,
                               ObservableList<ReadOnlyPerson> personList) {
        this.mainController = mainController;
        this.modelManager = modelManager;
        filteredPersonList = new FilteredList<>(personList, new PredExpr(new TrueQualifier())::satisfies);

        personListView.setItems(filteredPersonList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        loadGithubProfilePageWhenPersonIsSelected(mainController);
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
    
    @Subscribe
    private void handleFilterCommittedEvent(FilterCommittedEvent fce) {
        filteredPersonList.setPredicate(fce.filterExpression::satisfies);
    }

    private void loadGithubProfilePageWhenPersonIsSelected(MainController mainController) {
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.debug("Person in list view clicked. Loading GitHub profile page: '{}'", newValue);
                mainController.loadGithubProfilePage(newValue);
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

    /**
     * Informs user if selection is invalid
     * @return true if selection is valid, false otherwise
     */
    private boolean checkAndHandleInvalidSelection() {
        final List<?> selected = personListView.getSelectionModel().getSelectedItems();
        if (selected.isEmpty() || selected.stream().anyMatch(Objects::isNull)) {
            showNoValidSelectionAlert();
            return false;
        }
        return true;
    }

    private String collateNames(List<ReadOnlyPerson> list) {
        StringBuilder sb = new StringBuilder();
        list.stream().forEach(p -> sb.append(p.fullName() + ", "));
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        Platform.runLater(() -> {
            jumpToListItem(event.targetIndex);
        });
    }

    /**
     * Jumps the Nth item of the list if it exists. No action if the Nth item does not exist.
     * Jumps to the bottom if {@code targetIndex = -1}
     *
     * @param targetIndex starts from 1. To jump to 1st item, targetIndex should be 1.
     *                    To jump to bottom, should be -1.
     */

    private void jumpToListItem(int targetIndex) {
        int listSize = personListView.getItems().size();
        if (listSize < targetIndex) {
            return;
        }
        int indexOfItem;
        if (targetIndex == -1) {  // if the target is the bottom of the list
            indexOfItem = listSize - 1;
        } else {
            indexOfItem = targetIndex - 1; //to account for list indexes starting from 0
        }

        selectItem(indexOfItem);
    }

    private void showNoValidSelectionAlert() {
        mainController.showAlertDialogAndWait(AlertType.WARNING,
                "Invalid Selection", "No Person Selected", "Please select a person in the list.");
    }

    /**
     * Selects the item in the list and scrolls to it if it is out of view.
     * @param indexOfItem
     */
    private void selectItem(int indexOfItem) {
        personListView.getSelectionModel().clearAndSelect(indexOfItem);
        personListView.getFocusModel().focus(indexOfItem);
        personListView.requestFocus();
        personListView.scrollTo(indexOfItem);
    }
}

package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ModulePanelSelectionChangedEvent;
import seedu.address.model.module.Module;


/**
 * Describes the design of second UI panel containing module list display.
 */
public class ModuleListPanel2 extends UiPart<Region> {
    private static final String FXML = "ModuleListPanel2.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel2.class);

    @FXML
    private ListView<Module> moduleListView;

    public ModuleListPanel2(ObservableList<Module> moduleList) {
        super(FXML);
        setConnections(moduleList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Module> moduleList) {
        moduleListView.setItems(moduleList);
        moduleListView.setCellFactory(listView -> new ModuleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        moduleListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in module list panel changed to : '" + newValue + "'");
                        raise(new ModulePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ModuleCard} at the {@code index} and selects it.
     */
    //private void scrollTo(int index) {
    //Platform.runLater(() -> {
    //moduleListView.scrollTo(index);
    //moduleListView.getSelectionModel().clearAndSelect(index);
    //});
    //}


    //@Subscribe
    //private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
    //logger.info(LogsCenter.getEventHandlingLogMessage(event));
    //scrollTo(event.targetIndex);
    //}

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleCard2(module).getRoot());
            }
        }
    }


}

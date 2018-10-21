package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.LedgerPanelSelectionChangedEvent;
import seedu.address.model.ledger.Ledger;

import java.util.logging.Logger;

/**
 * Panel containing the list of ledgers.
 */
public class LedgerListPanel extends UiPart<Region> {
    private static final String FXML = "LedgerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LedgerListPanel.class);

    @FXML
    private ListView<Ledger> ledgerListView;

    public LedgerListPanel(ObservableList<Ledger> ledgerList) {
        super(FXML);
        setConnections(ledgerList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Ledger> ledgerList) {
        ledgerListView.setItems(ledgerList);
        ledgerListView.setCellFactory(listView -> new LedgerListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        ledgerListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.info("Selection in ledger list panel changed to : '" + newValue + "'");
                        raise(new LedgerPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code LedgerCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            ledgerListView.scrollTo(index);
            ledgerListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Ledger} using a {@code LedgerCard}.
     */
    class LedgerListViewCell extends ListCell<Ledger> {
        @Override
        protected void updateItem(Ledger ledger, boolean empty) {
            super.updateItem(ledger, empty);

            if (empty || ledger == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LedgerCard(ledger, getIndex() + 1).getRoot());
            }
        }
    }

}

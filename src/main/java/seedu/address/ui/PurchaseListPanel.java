package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.purchase.Purchase;

/**
 * Panel containing the list of purchases.
 */
public class PurchaseListPanel extends UiPart<Region> {

    private static final String FXML = "PurchaseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PurchaseListPanel.class);

    @FXML
    private ListView<Purchase> purchaseListView;

    public PurchaseListPanel(ObservableList<Purchase> purchaseList, ObservableValue<Purchase> selectedPurchase,
                           Consumer<Purchase> onSelectedPurchaseChange) {
        super(FXML);
        purchaseListView.setItems(purchaseList);
        purchaseListView.setCellFactory(listView -> new PurchaseListViewCell());
        purchaseListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in purchase list panel changed to : '" + newValue + "'");
            onSelectedPurchaseChange.accept(newValue);
        });
        selectedPurchase.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected purchase changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected purchase,
            // otherwise we would have an infinite loop.
            if (Objects.equals(purchaseListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                purchaseListView.getSelectionModel().clearSelection();
            } else {
                int index = purchaseListView.getItems().indexOf(newValue);
                purchaseListView.scrollTo(index);
                purchaseListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Purchase} using a {@code PurchaseCard}.
     */
    class PurchaseListViewCell extends ListCell<Purchase> {
        @Override
        protected void updateItem(Purchase purchase, boolean empty) {
            super.updateItem(purchase, empty);

            if (empty || purchase == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PurchaseCard(purchase, getIndex() + 1).getRoot());
            }
        }
    }
}

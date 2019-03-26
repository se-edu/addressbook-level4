package seedu.address.ui;


import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.purchase.Purchase;

/**
 * An UI component that displays information of a {@code Purchase}.
 */
public class PurchaseCard extends UiPart<Region> {

    private static final String FXML = "PurchaseListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Purchase purchase;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label price;
    @FXML

    private FlowPane tags;

    public PurchaseCard(Purchase purchase, int displayedIndex) {
        super(FXML);
        this.purchase = purchase;
        id.setText(displayedIndex + ". ");
        name.setText(purchase.getPurchaseName().fullName);
        price.setText(purchase.getPrice().value);
        purchase.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PurchaseCard)) {
            return false;
        }

        // state check
        PurchaseCard card = (PurchaseCard) other;
        return id.getText().equals(card.id.getText())
                && purchase.equals(card.purchase);
    }
}

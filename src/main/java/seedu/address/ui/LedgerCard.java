package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.ledger.Ledger;

/**
 * An UI component that displays information of a {@code Ledger}.
 */
public class LedgerCard extends UiPart<Region> {

    private static final String FXML = "LedgerListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exceptions will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Ledger ledger;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label id;
    @FXML
    private Label balance;
    @FXML
    private Label credit;
    @FXML
    private Label debit;
    @FXML
    private FlowPane tags;

    public LedgerCard(Ledger ledger, int displayedIndex) {
        super(FXML);
        this.ledger = ledger;
        //id.setText(null);
        //date.setText("Hello");
        date.setText("Date: " + ledger.getDateLedger().getDate());
        balance.setText("  Balance: $" + ledger.getAccount().getBalance());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LedgerCard)) {
            return false;
        }

        // state check
        LedgerCard card = (LedgerCard) other;
        return id.getText().equals(card.id.getText())
                && ledger.equals(card.ledger);
    }
}

package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ledger.Ledger;

/**
 * Represents a selection change in the Ledger List Panel
 */
public class LedgerPanelSelectionChangedEvent extends BaseEvent {


    private final Ledger newSelection;

    public LedgerPanelSelectionChangedEvent(Ledger newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Ledger getNewSelection() {
        return newSelection;
    }
}

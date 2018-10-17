package seedu.address.model.ledger;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Ledger in the Club Book
 */

public class Ledger {
    private final Account account;
    private final DateLedger dateLedger;

    public Ledger(DateLedger dateLedger, Account account) {
        requireAllNonNull(dateLedger);
        this.account = account;
        this.dateLedger = dateLedger;
    }

    public Account getAccount() {
        return account;
    }

    public DateLedger getDateLedger() {
        return dateLedger;
    }

    /**
     * Returns true if both ledgers of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameLedger(Ledger otherLedger) {
        if (otherLedger.getDateLedger() == dateLedger) {
            return true;
        }

        return otherLedger.getDateLedger() != null
                && otherLedger.getDateLedger().equals(getDateLedger());
    }

}

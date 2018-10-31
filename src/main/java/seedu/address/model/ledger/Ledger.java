package seedu.address.model.ledger;

import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Ledger in the Club Book
 */

public class Ledger {

    private final Logger logger = LogsCenter.getLogger(Ledger.class);

    private final Account account;
    private final DateLedger dateLedger;

    public Ledger(DateLedger dateLedger, Account account) {
        requireAllNonNull(dateLedger, account);
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
     * Returns true if both ledgers have the same date.
     */
    public boolean isSameLedger(Ledger otherLedger) {


        if (otherLedger.getDateLedger().getDate().equals(this.getDateLedger().getDate())) {
            return true;
        }

        /*
        return otherLedger != null
                && otherLedger.getDateLedger().getDate().equals(this.getDateLedger().getDate());
        */

        return false;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDateLedger().getDate());
        return builder.toString();
    }

}

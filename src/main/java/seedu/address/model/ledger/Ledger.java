package seedu.address.model.ledger;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Ledger {
    //private final Account account;
    private final DateLedger dateLedger;

    public Ledger(DateLedger dateLedger) {
        requireAllNonNull(dateLedger);
        //this.account = account;
        this.dateLedger = dateLedger;
    }

    /*
    public Account getAccount() {
        return account;
    }
    */

    public DateLedger getDateLedger() {
        return dateLedger;
    }

}

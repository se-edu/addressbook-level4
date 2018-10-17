package seedu.address.model.ledger.exceptions;

/**
 * Signals that the operation will result in duplicate Ledgers (Ledgers are considered duplicates if they have the same
 * date).
 */
public class DuplicateLedgerException extends RuntimeException {
    public DuplicateLedgerException() {
        super("Operation would result in duplicate persons");
    }
}

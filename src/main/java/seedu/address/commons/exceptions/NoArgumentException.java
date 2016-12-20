package seedu.address.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class NoArgumentException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public NoArgumentException(String message) {
        super(message);
    }
}

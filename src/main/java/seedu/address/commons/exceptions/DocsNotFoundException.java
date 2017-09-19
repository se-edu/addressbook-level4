package seedu.address.commons.exceptions;

/**
 * Signals that a required documentation file is missing.
 */
public class DocsNotFoundException extends RuntimeException {
    public DocsNotFoundException(String message) {
        super(message);
    }
}

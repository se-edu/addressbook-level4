package seedu.address.model.person.exceptions;

//@@author ChoChihTun
/**
 * Signals that the input duration format is invalid
 */
public class DurationParseException extends Exception {
    public DurationParseException(String message) {
        super(message);
    }
}

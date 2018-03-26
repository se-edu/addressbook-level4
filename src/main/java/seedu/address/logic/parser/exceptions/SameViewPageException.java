package seedu.address.logic.parser.exceptions;

//@@author ChoChihTun
/**
 * Signals that the input view page time unit clashes with current time unit
 */
public class SameViewPageException extends Exception {
    public SameViewPageException(String message) {
        super(message);
    }

}

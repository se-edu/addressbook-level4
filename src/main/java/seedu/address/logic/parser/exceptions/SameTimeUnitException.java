package seedu.address.logic.parser.exceptions;

//@@author ChoChihTun
/**
 * Signals that the input calendar view page time unit clashes with current time unit
 */
public class SameTimeUnitException extends Exception {
    public SameTimeUnitException(String message) {
        super(message);
    }

}

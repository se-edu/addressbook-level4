package seedu.address.model.person.exceptions;

/**
 * Signals that there is a clash of timing in the schedule
 */
public class TimingClashException extends Exception {

    public TimingClashException(String message) {
        super(message);
    }
}

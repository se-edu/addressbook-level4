package seedu.address.model.task.exceptions;

/**
 * Signals that the input date is invalid. They should follow the format of
 * DDMMYY
 */

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        super("Date is either invalid or not " +
            "in the ddMMyy format.");
    }
}

package seedu.address.model.task.exceptions;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() { super("Date is either invalid or not in the ddMMyy format.");}
}

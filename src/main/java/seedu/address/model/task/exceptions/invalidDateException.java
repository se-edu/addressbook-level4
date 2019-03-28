package seedu.address.model.task.exceptions;

public class invalidDateException extends RuntimeException {
    public invalidDateException() { super("Date is not in the ddMMyy format.");}
}

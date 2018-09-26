package seedu.address.model.leave.exceptions;

public class DuplicateLeaveException extends RuntimeException {
    public DuplicateLeaveException() {
        super("Operation would result in duplicate leave request");
    }
}
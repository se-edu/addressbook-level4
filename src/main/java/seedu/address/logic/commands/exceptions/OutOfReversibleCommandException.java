package seedu.address.logic.commands.exceptions;

/**
 * Signals that the operation is unable to find a {@code ReversibleCommand} to operate on.
 */
public class OutOfReversibleCommandException extends CommandException {
    public OutOfReversibleCommandException(String message) {
        super(message);
    }
}

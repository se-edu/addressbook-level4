package seedu.address.model.module.exceptions;

/**
 * Signals that the operation will result in duplicate {@link Module} objects.
 */
public class DuplicateModuleException extends RuntimeException {
    public DuplicateModuleException() {
        super("Operation would result in duplicate modules");
    }
}

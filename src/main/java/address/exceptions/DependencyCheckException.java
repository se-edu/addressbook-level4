package address.exceptions;

/**
 * Represents a failure in checking application dependency
 */
public class DependencyCheckException extends Exception {
    public DependencyCheckException(Exception cause) {
        super(cause);
    }

    public DependencyCheckException(String cause) {
        super(cause);
    }
}

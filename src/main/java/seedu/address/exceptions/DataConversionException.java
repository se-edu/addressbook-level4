package seedu.address.exceptions;

/**
 * Represents an error during conversion of data from one format to another
 */
public class DataConversionException extends Exception {
    public DataConversionException(Exception cause) {
        super(cause);
    }

    public DataConversionException(String cause) {
        super(cause);
    }
}

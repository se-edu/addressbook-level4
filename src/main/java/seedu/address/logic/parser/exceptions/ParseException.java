package seedu.address.logic.parser.exceptions;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }
}

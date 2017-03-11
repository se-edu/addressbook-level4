package seedu.address.logic.parser;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseErrorException extends Exception {
    public ParseErrorException(String message) {
        super(message);
    }
}

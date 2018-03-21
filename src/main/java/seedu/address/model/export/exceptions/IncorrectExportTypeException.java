package seedu.address.model.export.exceptions;

public class IncorrectExportTypeException extends IllegalArgumentException {
    public IncorrectExportTypeException() {super("Unable to map given argument to a valid Export Type");}
}

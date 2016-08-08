package address.exceptions;

public class DuplicateDataException extends Exception {

    private final String cause;

    public DuplicateDataException(String cause) {
        this.cause = cause;
    }

    protected DuplicateDataException() {
        cause = "Unspecified cause";
    }

    @Override
    public String toString() {
        return "This action would result in duplicate data items: " + cause;
    }
}

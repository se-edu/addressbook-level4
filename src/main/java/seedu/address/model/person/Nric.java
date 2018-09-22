package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the unique identity of a person in the address book.
 */
public class Nric {
    public static final String MESSAGE_NRIC_CONSTRAINTS =
            "NRIC should contain a prefix containing 'S', 'T', 'F' or 'G', followed by a seven-digit number,"
                    + " followed by an alphabetical suffix; it should not be blank.";
    /*
     * Prefix should be a S, T, F or G, followed by a 7-digit number, followed by an alphabetical suffix.
     */
    public static final String NRIC_VALIDATION_REGEX = "^[STFG]\\d{7}[A-Z]$";
    public final String nric;

    public Nric(String nric) {
        requireNonNull(nric);

        //Checks for valid NRIC regex. if false, throws IllegalArgumentException
        checkArgument(isValidNric(nric), MESSAGE_NRIC_CONSTRAINTS);
        this.nric = nric;
    }

    /**
     * Returns true if a given string is a valid NRIC.
     */
    public static boolean isValidNric(String test) {
        return test.matches(NRIC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return nric;
    }

    @Override
    public int hashCode() {
        return nric.hashCode();
    }
}

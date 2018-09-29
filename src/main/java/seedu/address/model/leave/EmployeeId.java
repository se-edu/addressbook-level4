package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Leave's nric in the leave list.
 * Guarantees: immutable; is valid as declared in
 */
public class EmployeeId {

    public final String nric;
    public static final String NRIC_VALIDATION_REGEX = "^[STFG]\\d{7}[A-Z]$";
    public static final String MESSAGE_NRIC_CONSTRAINTS = "Incorrect NRIC format.";

    /**
     * Constructs an {@code EmployeeId}.
     *
     * @param nric A valid nric.
     */
    public EmployeeId(String nric) {
        requireNonNull(nric);
        this.nric = nric;
    }

    @Override
    public String toString() {
        return nric;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeId// instanceof handles nulls
                && nric.equals(((EmployeeId) other).nric)); // state check
    }

    /**
     * Returns true if a given string is a valid nric.
     */
    public static boolean isValidNRIC(String test) {
        return test.matches(NRIC_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return nric.hashCode();
    }
}

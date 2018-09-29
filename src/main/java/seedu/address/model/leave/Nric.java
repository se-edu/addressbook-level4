package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Leave's nric in the leave list.
 * Guarantees: immutable; is valid as declared in
 */
public class Nric {

    public final String nric;

    /**
     * Constructs an {@code Nric}.
     *
     * @param nric A valid nric.
     */
    public Nric(String nric) {
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
                || (other instanceof Nric// instanceof handles nulls
                && nric.equals(((Nric) other).nric)); // state check
    }

    @Override
    public int hashCode() {
        return nric.hashCode();
    }
}

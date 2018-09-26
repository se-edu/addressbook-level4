package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

public class NRIC {

    public final String nric;


    public NRIC (String nric){
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
                || (other instanceof NRIC// instanceof handles nulls
                && nric.equals(((NRIC) other).nric)); // state check
    }

    @Override
    public int hashCode() {
        return nric.hashCode();
    }
}

package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

public class Approval {


    public final String status;


    public Approval(String status){
        requireNonNull(status);
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Approval // instanceof handles nulls
                && status.equals(((Approval) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }


}

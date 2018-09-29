package seedu.address.model.leave;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Leave in the leave list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Leave {

    private final Date date;
    private final Nric nric;
    private final Approval approval;

    /**
     * Every field must be present and not null.
     */
    public Leave(Nric nric, Date date, Approval approval) {
        requireAllNonNull(nric, date, approval);
        this.nric = nric;
        this.date = date;
        this.approval = approval;
    }

    public Date getDate() {
        return date;
    }

    public Nric getNric() {
        return nric;
    }

    public Approval getApproval() {
        return approval;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, nric, approval);
    }

    /**
     * Returns true if both leave of the same nric have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two leaves.
     */

    public boolean isSameRequest(Leave otherRequest) {
        if (otherRequest == this) {
            return true;
        }

        return otherRequest != null
                && otherRequest.getNric().equals(getNric())
                && (otherRequest.getDate().equals(getDate()));
    }

    /**
     * Returns true if both leaves have the same identity and data fields.
     * This defines a stronger notion of equality between two leaves.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Leave)) {
            return false;
        }

        Leave otherPerson = (Leave) other;
        return otherPerson.getNric().equals(getNric())
                && otherPerson.getDate().equals(getDate())
                && otherPerson.getApproval().equals(getApproval());
    }


}

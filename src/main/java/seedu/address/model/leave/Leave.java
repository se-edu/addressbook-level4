package seedu.address.model.leave;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Leave {

    private final Date date;
    private final NRIC nric;
    private final Approval approval;


    public Leave(NRIC nric, Date date, Approval approval){
        requireAllNonNull(nric,date,approval);
        this.nric = nric;
        this.date = date;
        this.approval = approval;
    }

    public Date getDate() {return date; }

    public NRIC getNric() {return nric; }

    public Approval getApproval() {return approval; }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, nric, approval);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
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
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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

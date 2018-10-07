package seedu.address.testutil;

import seedu.address.model.leave.Approval;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.EmployeeId;
import seedu.address.model.leave.Leave;

/**
 * A utility class to help with building Leave objects.
 */
public class LeaveBuilder {
    public static final String DEFAULT_NRIC = "S1111111A";
    public static final String DEFAULT_DATE = "30/12/2018";
    public static final String DEFAULT_APPROVAL = "PENDING";

    private EmployeeId nric;
    private Date date;
    private Approval approval;


    public LeaveBuilder() {
        nric = new EmployeeId(DEFAULT_NRIC);
        date = new Date(DEFAULT_DATE);
        approval = new Approval(DEFAULT_APPROVAL);
    }

    /**
     * Initializes the LeaveBuilder with the data of {@code leaveToCopy}.
     */
    public LeaveBuilder(Leave leaveToCopy) {
        nric = leaveToCopy.getEmployeeId();
        date = leaveToCopy.getDate();
        approval = leaveToCopy.getApproval();
    }

    /**
     * Sets the {@code Nric} of the {@code Leave} that we are building.
     */
    public LeaveBuilder withNric(String nric) {
        this.nric = new EmployeeId(nric);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Leave} that we are building.
     */
    public LeaveBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Approval} of the {@code Leave} that we are building.
     */
    public LeaveBuilder withApproval(String approval) {
        this.approval = new Approval(approval);
        return this;
    }

    public Leave build() {
        return new Leave(nric, date, approval);
    }

}

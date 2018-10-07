package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.LeaveList;
import seedu.address.model.leave.Leave;

/**
 * A utility class containing a list of {@code Leave} objects to be used in tests.
 */
public class TypicalLeave {

    public static final Leave REQUEST_1 = new LeaveBuilder().withNric("S1234567A").withDate("01/02/2018")
            .withApproval("PENDING").build();
    public static final Leave REQUEST_2 = new LeaveBuilder().withNric("S1234597A").withDate("01/10/2018")
            .withApproval("PENDING").build();

    //Manually add
    public static final Leave REQUEST_3 = new LeaveBuilder().withNric("S1234591A").withDate("01/10/2017")
            .withApproval("PENDING").build();
    public static final Leave REQUEST_4 = new LeaveBuilder().withNric("S1234591Z").withDate("01/10/2017")
            .withApproval("PENDING").build();


    private TypicalLeave() {} // prevents instantiation

    /**
     * Returns an {@code LeaveList} with all the typical leaves.
     */
    public static LeaveList getTypicalLeaveList() {
        LeaveList leaveList = new LeaveList();
        for (Leave leave : getTypicalLeaves()) {
            leaveList.addRequest(leave);
        }
        return leaveList;
    }

    public static List<Leave> getTypicalLeaves() {
        return new ArrayList<>(Arrays.asList(REQUEST_1, REQUEST_2));
    }

}

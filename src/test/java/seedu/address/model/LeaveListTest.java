package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalLeave.getTypicalLeaveList;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.leave.Leave;

public class LeaveListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final LeaveList leaveList = new LeaveList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), leaveList.getRequestList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        leaveList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyLeaveList_replacesData() {
        LeaveList newData = getTypicalLeaveList();
        leaveList.resetData(newData);
        assertEquals(newData, leaveList);
    }

    @Test
    public void getLeaveList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        leaveList.getRequestList().remove(0);
    }

    /**
     * A stub ReadOnlyLeaveList whose leaves list can violate interface constraints.
     */
    private static class LeaveListStub implements ReadOnlyLeaveList {
        private final ObservableList<Leave> leaves = FXCollections.observableArrayList();

        LeaveListStub(Collection<Leave> leaves) {
            this.leaves.setAll(leaves);
        }

        @Override
        public ObservableList<Leave> getRequestList() {
            return leaves;
        }
    }




}

package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.leave.Leave;

public interface ReadOnlyLeaveList {
    ObservableList<Leave> getRequestList();

}

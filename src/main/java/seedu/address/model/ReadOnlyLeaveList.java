package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.leave.Leave;

/**
 * Unmodifiable view of an leave list
 */
public interface ReadOnlyLeaveList {

    /**
     * Returns an unmodifiable view of the leave list.
     * This list will not contain any duplicate leave.
     */
    ObservableList<Leave> getRequestList();

}

package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.LeaveList;
import seedu.address.model.ReadOnlyLeaveList;
import seedu.address.model.leave.Leave;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "leavelist")
public class XmlSerializableLeaveList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate request(s).";

    @XmlElement
    private List<XmlAdaptedLeave> leaves;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableLeaveList() {
        leaves = new ArrayList<>();
    }
    /**
     * Conversion
     */
    public XmlSerializableLeaveList(ReadOnlyLeaveList src) {
        this();
        leaves.addAll(src.getRequestList().stream().map(XmlAdaptedLeave::new).collect(Collectors.toList()));
    }
    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */

    public LeaveList toModelType() throws IllegalValueException {
        LeaveList requests = new LeaveList();
        for (XmlAdaptedLeave p : leaves) {
            Leave leave = p.toModelType();
            if (requests.hasRequest(leave)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            requests.addRequest(leave);
        }
        return requests;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableLeaveList)) {
            return false;
        }
        return leaves.equals(((XmlSerializableLeaveList) other).leaves);
    }
}


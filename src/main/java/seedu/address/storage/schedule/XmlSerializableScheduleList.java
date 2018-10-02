package seedu.address.storage.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleList;


/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "schedulelist")
public class XmlSerializableScheduleList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    @XmlElement
    private List<XmlAdaptedSchedule> schedules;

    /**
     * Creates an empty XmlSerializableScheduleList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableScheduleList() {
        schedules = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableScheduleList(ReadOnlyScheduleList src) {
        this();
        schedules.addAll(src.getScheduleList().stream().map(XmlAdaptedSchedule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this ScheduleList into the model's {@code ScheduleList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public ScheduleList toModelType() throws IllegalValueException {
        ScheduleList scheduleList = new ScheduleList();
        for (XmlAdaptedSchedule p : schedules) {
            Schedule schedule = p.toModelType();
            if (scheduleList.hasSchedule(schedule)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            scheduleList.addSchedule(schedule);
        }
        return scheduleList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableScheduleList)) {
            return false;
        }
        return schedules.equals(((XmlSerializableScheduleList) other).schedules);
    }
}

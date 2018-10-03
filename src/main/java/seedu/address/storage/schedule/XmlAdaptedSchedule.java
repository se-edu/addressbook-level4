package seedu.address.storage.schedule;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;
import seedu.address.storage.addressbook.XmlAdaptedPerson;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedSchedule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Schedule's %s field is missing!";

    @XmlElement(required = true)
    private String employeeId;
    @XmlElement(required = true)
    private String type;
    @XmlElement(required = true)
    private String date;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSchedule() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedSchedule(String employeeId, String type, String date) {
        this.employeeId = employeeId;
        this.type = type;
        this.date = date;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedSchedule(Schedule source) {
        employeeId = source.getId().value;
        date = source.getDate().date;
        type = source.getType().value;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {

        if (employeeId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeId.class.getSimpleName()));
        }
        if (!EmployeeId.isValidEmployeeId(employeeId)) {
            throw new IllegalValueException(EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS);
        }
        final EmployeeId modelEmployeeId = new EmployeeId(employeeId);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_TYPE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        return new Schedule(modelEmployeeId, modelType, modelDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedSchedule otherPerson = (XmlAdaptedSchedule) other;
        return Objects.equals(employeeId, otherPerson.employeeId)
                && Objects.equals(date, otherPerson.date)
                && Objects.equals(type, otherPerson.type);
    }
}

package seedu.address.storage;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Task;
import seedu.address.model.personal.PersonalTask;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";
    private LocalDateTime placeholder;

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String duration;
    @XmlElement(required = true)
    private String dateandtime;
    /*
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    */
    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with given task details.
     */
    public XmlAdaptedTask(String description, String duration, LocalDateTime dateandtime) {
        this.description = description;
        this.duration = duration;
        this.dateandtime = dateandtime.toString();
        this.placeholder = dateandtime;
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     *
     */
    public XmlAdaptedTask(Task source) {
        description = source.getDescription();
        duration = source.getDuration();
        dateandtime = source.getTaskDateTime().toString();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     * Because of the way Task was designed (As an interface), i'm forced to just input this as a PersonalTask
     * until a better solution can be found
     */

    public Task toModelType() throws IllegalValueException {

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Task.MESSAGE_DESCRIPTION_CONSTRAINTS));
        }
        if (this.duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Task.MESSAGE_DURATION_CONSTRAINTS));
        }
        // Look into putting a test for localdateandtime. Can't use timingclash function with a string.

        return new PersonalTask(placeholder,duration,description);
    }

}
package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Task;
import seedu.address.model.personal.PersonalTask;
import seedu.address.model.tutee.TuitionTask;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String duration;
    @XmlElement(required = true)
    private String dateAndTime;
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
     * Constructs an {@code XmlAdaptedTask} with given personal task details.
     */
    public XmlAdaptedTask(String description, String duration, LocalDateTime dateAndTime) {
        this.description = description;
        this.duration = duration;
        this.dateAndTime = dateAndTime.toString();
    }

    public XmlAdaptedTask(String name, String description, String duration, LocalDateTime dateAndTime) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.dateAndTime = dateAndTime.format(formatter);
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     */
    public XmlAdaptedTask(Task source) {
        description = source.getDescription();
        duration = source.getDuration();
        dateAndTime = source.getTaskDateTime().format(formatter);
        if (source instanceof TuitionTask) {
            name = ((TuitionTask) source).getPerson();
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     * Because of the way Task was designed (As an interface), i'm forced to just input this as a PersonalTask
     * until a better solution can be found
     */

    public Task toModelType() throws IllegalValueException {
        LocalDateTime taskDateTime = LocalDateTime.parse(dateAndTime, formatter);
        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Task.MESSAGE_DESCRIPTION_CONSTRAINTS));
        }
        if (this.duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Task.MESSAGE_DURATION_CONSTRAINTS));
        }
        if (this.dateAndTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Task.MESSAGE_DATETIME_CONSTRAINTS));
        }
        if (this.name == null) {
            return new PersonalTask(taskDateTime, duration, description);
        } else {
            return new TuitionTask(name, taskDateTime, duration, description);
        }
    }
}

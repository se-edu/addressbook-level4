package seedu.todoList.storage;

import seedu.todoList.model.task.*;
import seedu.todoList.model.tag.Tag;
import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.commons.exceptions.IllegalValueException;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedEvent extends XmlAdaptedTask {
    
	@XmlElement(required = true)
	Date date;
	@XmlElement(required = true)
    StartTime startTime;
	@XmlElement(required = true)
    EndTime endTime;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedEvent () {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedtask
     */
    public XmlAdaptedEvent(ReadOnlyTask source) {
    	date = source.getDate();
        startTime = source.getStartTime();
        endTime = source.getEndTime();
    }

    @Override
    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(super.name);
        final Date date = new Date(this.date);
        final StartTime startTime = new StartTime(this.startTime);
        final EndTime endTime = new EndTime(this.endTime);
        return new Task(name, date, startTime, endTime);
    }
}


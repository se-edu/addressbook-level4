package seedu.todoList.storage;

import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
import seedu.todoList.model.task.attributes.StartTime;
import seedu.todoList.commons.exceptions.IllegalValueException;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedEvent extends XmlAdaptedTask {
    
	@XmlElement(required = true)
	private String date;
	@XmlElement(required = true)
    private String startTime;
	@XmlElement(required = true)
    private String endTime;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedEvent () {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedtask
     */
    public XmlAdaptedEvent(Event source) {
    	date = source.getDate().value;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
    }
    
    public XmlAdaptedEvent(ReadOnlyTask source) {
    	this((Event) source);
    }
    
    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(super.name);
        final Date date = new Date(this.date);
        final StartTime startTime = new StartTime(this.startTime);
        final EndTime endTime = new EndTime(this.endTime);
        return new Event(name, date, startTime, endTime);
    }
}


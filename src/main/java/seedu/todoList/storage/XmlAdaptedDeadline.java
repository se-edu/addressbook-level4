package seedu.todoList.storage;

import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
import seedu.todoList.model.task.attributes.EndTime;
import seedu.todoList.commons.exceptions.IllegalValueException;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedDeadline extends XmlAdaptedTask {
    
	@XmlElement(required = true)
	private String date;
	@XmlElement(required = true)
    private String endTime;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedDeadline () {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedtask
     */
    public XmlAdaptedDeadline(Deadline source) {
    	date = source.getDate().value;
        endTime = source.getEndTime().value;
    }
    
    public XmlAdaptedDeadline(ReadOnlyTask source) {
    	this((Deadline) source);
    }

    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(super.name);
        final Date date = new Date(this.date);
        final EndTime endTime = new EndTime(this.endTime);
        return new Deadline(name, date, endTime);
    }
}


package seedu.todoList.storage;

import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
import seedu.todoList.model.task.attributes.Priority;
import seedu.todoList.commons.exceptions.IllegalValueException;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedTodo extends XmlAdaptedTask {
    
	@XmlElement(required = true)
	private String priority;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTodo () {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedtask
     */
    public XmlAdaptedTodo(Todo source) {
    	super.name = source.getName().value;
    	this.priority = source.getPriority().toString();
    }
    
    public XmlAdaptedTodo(ReadOnlyTask source) {
    	this((Todo) source);
    }

    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(super.name);
        final Priority priority = new Priority(this.priority);
        return new Todo(name, priority);
    }
}


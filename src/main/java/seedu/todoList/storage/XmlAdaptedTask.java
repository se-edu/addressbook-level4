package seedu.todoList.storage;

import seedu.todoList.model.task.*;
import seedu.todoList.commons.exceptions.IllegalValueException;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the task.
 */
public abstract class XmlAdaptedTask {
    
    @XmlElement(required = true)
    protected String name;


    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask () {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedtask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public abstract Task toModelType() throws IllegalValueException;
}

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
    
<<<<<<< HEAD
    @XmlElement(required = true)
    protected String name;
=======
    @XmlElement(required = true)
    private String Todo;
    @XmlElement(required = true)
    private String Priority;
    @XmlElement(required = true)
    private String StartTime;
    @XmlElement(required = true)
    private String EndTime;
    @XmlElement(required = true)
    private String Date;
>>>>>>> e60184ee291f8238357c383073cb787221a2d62e


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
<<<<<<< HEAD
        name = source.getName().fullName;
=======
        Todo = source.getTodo().todo;
        Priority = source.getPriority().priority;
        StartTime = source.getStartTime().startTime;
        EndTime = source.getEndTime().endTime;
>>>>>>> e60184ee291f8238357c383073cb787221a2d62e
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
<<<<<<< HEAD
    public abstract Task toModelType() throws IllegalValueException;
=======
    public Task toModelType() throws IllegalValueException {
        final Todo Todo = new Todo(this.Todo);
        final Priority priority = new Priority(this.Priority);
        final StartTime startTime = new StartTime(this.StartTime);
        final EndTime endTime = new EndTime(this.EndTime);
        return new Task(Todo, priority, startTime, endTime);
    }
>>>>>>> e60184ee291f8238357c383073cb787221a2d62e
}

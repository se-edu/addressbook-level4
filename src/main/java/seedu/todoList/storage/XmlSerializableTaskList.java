package seedu.todoList.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.ReadOnlyTaskList;
import seedu.todoList.model.task.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable TaskList that is serializable to XML format
 */
@XmlRootElement(name = "TaskList")
public class XmlSerializableTaskList implements ReadOnlyTaskList {

    @XmlElement
    private List<XmlAdaptedTask> tasks;

    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskList() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskList(ReadOnlyTaskList src) {
    	Object[] typeCheck = src.getTaskList().toArray();
    	if(typeCheck[0] instanceof Todo) {
    		tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTodo::new).collect(Collectors.toList()));
    	}
    	else if(typeCheck[0] instanceof Event) {
    		tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTodo::new).collect(Collectors.toList()));
    	}
    	else if(typeCheck[0] instanceof Deadline) {
    		tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTodo::new).collect(Collectors.toList()));
    	}
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}

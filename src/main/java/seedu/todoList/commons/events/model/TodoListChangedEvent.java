package seedu.todoList.commons.events.model;

import seedu.todoList.commons.events.BaseEvent;
import seedu.todoList.model.ReadOnlyTodoList;

/** Indicates the TodoList in the model has changed*/
public class TodoListChangedEvent extends BaseEvent {

    public final ReadOnlyTodoList data;

    public TodoListChangedEvent(ReadOnlyTodoList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.gettaskList().size() + ", number of tags " + data.getTagList().size();
    }
}

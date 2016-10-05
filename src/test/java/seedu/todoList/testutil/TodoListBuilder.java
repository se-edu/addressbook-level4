package seedu.todoList.testutil;

import seedu.todoList.model.tag.Tag;
import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.TodoList;
import seedu.todoList.model.task.Task;
import seedu.todoList.model.task.UniqueTaskList;

/**
 * A utility class to help with building TodoList  objects.
 * Example usage: <br>
 *     {@code TodoList ab = new TodoListBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TodoListBuilder {

    private TodoList TodoList;

    public TodoListBuilder(TodoList TodoList){
        this.TodoList = TodoList;
    }

    public TodoListBuilder withTask(Task task) throws UniqueTaskList.DuplicatetaskException {
        TodoList.addTask(task);
        return this;
    }

    public TodoListBuilder withTag(String tagName) throws IllegalValueException {
        TodoList.addTag(new Tag(tagName));
        return this;
    }

    public TodoList build(){
        return TodoList;
    }
}

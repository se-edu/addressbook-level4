package seedu.todoList.testutil;

import seedu.todoList.model.tag.Tag;
import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.*;
/**
 *
 */
public class taskBuilder {

    private Testtask task;

    public taskBuilder() {
        this.task = new Testtask();
    }

    public taskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public taskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public taskBuilder withTodo(String Todo) throws IllegalValueException {
        this.task.setTodo(new Todo(Todo));
        return this;
    }

    public taskBuilder withPhone(String phone) throws IllegalValueException {
        this.task.setPhone(new Phone(phone));
        return this;
    }

    public taskBuilder withEmail(String email) throws IllegalValueException {
        this.task.setEmail(new Email(email));
        return this;
    }

    public Testtask build() {
        return this.task;
    }

}

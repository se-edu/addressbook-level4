package seedu.todoList.testutil;

import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class Testtask implements ReadOnlyTask {

    private Name name;
    private Todo Todo;
    private Email email;
    private Phone phone;
    private UniqueTagList tags;

    public Testtask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setTodo(Todo Todo) {
        this.Todo = Todo;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public Todo getTodo() {
        return Todo;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        sb.append("a/" + this.getTodo().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}

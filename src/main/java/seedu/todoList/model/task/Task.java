package seedu.todoList.model.task;

import java.util.Objects;

import seedu.todoList.commons.util.CollectionUtil;
import seedu.todoList.model.tag.UniqueTagList;

/**
 * Represents a task in the Todo book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private StartTime startTime;
    private EndTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Phone phone, Email email, Todo Todo, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, Todo, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.Todo = Todo;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getTodo(), source.getTags());
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
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, Todo, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}

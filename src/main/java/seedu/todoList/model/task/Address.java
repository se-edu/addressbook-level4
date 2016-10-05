package seedu.todoList.model.task;


import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * Represents a task's Todo in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidTodo(String)}
 */
public class Todo {
    
    public static final String MESSAGE_Todo_CONSTRAINTS = "task Todoes can be in any format";
    public static final String Todo_VALIDATION_REGEX = ".+";

    public final String value;

    /**
     * Validates given Todo.
     *
     * @throws IllegalValueException if given Todo string is invalid.
     */
    public Todo(String Todo) throws IllegalValueException {
        assert Todo != null;
        if (!isValidTodo(Todo)) {
            throw new IllegalValueException(MESSAGE_Todo_CONSTRAINTS);
        }
        this.value = Todo;
    }

    /**
     * Returns true if a given string is a valid task email.
     */
    public static boolean isValidTodo(String test) {
        return test.matches(Todo_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Todo // instanceof handles nulls
                && this.value.equals(((Todo) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}